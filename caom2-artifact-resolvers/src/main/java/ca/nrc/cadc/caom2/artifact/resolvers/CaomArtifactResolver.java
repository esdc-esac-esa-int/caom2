/*
 ************************************************************************
 *******************  CANADIAN ASTRONOMY DATA CENTRE  *******************
 **************  CENTRE CANADIEN DE DONNÉES ASTRONOMIQUES  **************
 *
 *  (c) 2019.                            (c) 2019.
 *  Government of Canada                 Gouvernement du Canada
 *  National Research Council            Conseil national de recherches
 *  Ottawa, Canada, K1A 0R6              Ottawa, Canada, K1A 0R6
 *  All rights reserved                  Tous droits réservés
 *
 *  NRC disclaims any warranties,        Le CNRC dénie toute garantie
 *  expressed, implied, or               énoncée, implicite ou légale,
 *  statutory, of any kind with          de quelque nature que ce
 *  respect to the software,             soit, concernant le logiciel,
 *  including without limitation         y compris sans restriction
 *  any warranty of merchantability      toute garantie de valeur
 *  or fitness for a particular          marchande ou de pertinence
 *  purpose. NRC shall not be            pour un usage particulier.
 *  liable in any event for any          Le CNRC ne pourra en aucun cas
 *  damages, whether direct or           être tenu responsable de tout
 *  indirect, special or general,        dommage, direct ou indirect,
 *  consequential or incidental,         particulier ou général,
 *  arising from the use of the          accessoire ou fortuit, résultant
 *  software.  Neither the name          de l'utilisation du logiciel. Ni
 *  of the National Research             le nom du Conseil National de
 *  Council of Canada nor the            Recherches du Canada ni les noms
 *  names of its contributors may        de ses  participants ne peuvent
 *  be used to endorse or promote        être utilisés pour approuver ou
 *  products derived from this           promouvoir les produits dérivés
 *  software without specific prior      de ce logiciel sans autorisation
 *  written permission.                  préalable et particulière
 *                                       par écrit.
 *
 *  This file is part of the             Ce fichier fait partie du projet
 *  OpenCADC project.                    OpenCADC.
 *
 *  OpenCADC is free software:           OpenCADC est un logiciel libre ;
 *  you can redistribute it and/or       vous pouvez le redistribuer ou le
 *  modify it under the terms of         modifier suivant les termes de
 *  the GNU Affero General Public        la “GNU Affero General Public
 *  License as published by the          License” telle que publiée
 *  Free Software Foundation,            par la Free Software Foundation
 *  either version 3 of the              : soit la version 3 de cette
 *  License, or (at your option)         licence, soit (à votre gré)
 *  any later version.                   toute version ultérieure.
 *
 *  OpenCADC is distributed in the       OpenCADC est distribué
 *  hope that it will be useful,         dans l’espoir qu’il vous
 *  but WITHOUT ANY WARRANTY;            sera utile, mais SANS AUCUNE
 *  without even the implied             GARANTIE : sans même la garantie
 *  warranty of MERCHANTABILITY          implicite de COMMERCIALISABILITÉ
 *  or FITNESS FOR A PARTICULAR          ni d’ADÉQUATION À UN OBJECTIF
 *  PURPOSE.  See the GNU Affero         PARTICULIER. Consultez la Licence
 *  General Public License for           Générale Publique GNU Affero
 *  more details.                        pour plus de détails.
 *
 *  You should have received             Vous devriez avoir reçu une
 *  a copy of the GNU Affero             copie de la Licence Générale
 *  General Public License along         Publique GNU Affero avec
 *  with OpenCADC.  If not, see          OpenCADC ; si ce n’est
 *  <http://www.gnu.org/licenses/>.      pas le cas, consultez :
 *                                       <http://www.gnu.org/licenses/>.
 *
 *  $Revision: 4 $
 *
 ************************************************************************
 */

package ca.nrc.cadc.caom2.artifact.resolvers;

import ca.nrc.cadc.auth.AuthMethod;
import ca.nrc.cadc.auth.AuthenticationUtil;
import ca.nrc.cadc.caom2.artifact.resolvers.util.ResolverUtil;
import ca.nrc.cadc.net.NetUtil;
import ca.nrc.cadc.net.StorageResolver;
import ca.nrc.cadc.net.Traceable;
import ca.nrc.cadc.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Utility class to invoke the appropriate StorageResolver to convert a URI to URL(s).
 * If no StorageResolver can be found, the URI.toURL() method is called as a fallback.
 *
 * @author pdowler
 * @author Raul Gutierrez-Sanchez
 */
public class CaomArtifactResolver {

    private static final Logger log = Logger.getLogger(CaomArtifactResolver.class);

    private static final String CONFIG_FILENAME = CaomArtifactResolver.class.getSimpleName() + ".properties";
    private static final String CONFIG_FILENAME_DEFAULT = CaomArtifactResolver.class.getSimpleName() + ".properties.default";

    protected final Map<String, StorageResolver> handlers = new HashMap<>();
    private final StorageResolver defaultResolver;

    private AuthMethod authMethod;
    private String runID;

    /**
     * Create a CaomArtifactResolver from the default config. By default, a resource named
     * CaomArtifactResolver.properties is found via the class loader that loaded this class.
     * If this configuration file is not found, the default one 
     * CaomArtifactResolver.properties.default is used.
     */
    public CaomArtifactResolver() {
        this(CaomArtifactResolver.class.getClassLoader().getResource(CONFIG_FILENAME));
    }

    /**
     * Create a CaomArtifactResolver with configuration loaded from the specified URL.
     * <p>The config resource has contains URIs (one per line, comments start line with #, blank lines
     * are ignored) with a scheme and a class name of a class that implements the StorageResolver
     * interface for that particular scheme.</p>
     *
     * @param config configuration file
     */
    public CaomArtifactResolver(File config) {
        try {
            if (!config.exists() || !config.canRead()) {
                throw new IllegalStateException("not found or not readable: " + config);
            }
            this.defaultResolver = readConfig(new FileInputStream(config));
            log.debug("default resolver: " + defaultResolver);
        } catch (IOException ex) {
            throw new IllegalStateException("CONFIG: failed to read config from " + config);
        }
    }
    
    /**
     * Create a CaomArtifactResolver with configuration loaded from the specified URL.
     * <p>The config resource has contains URIs (one per line, comments start line with #, blank lines
     * are ignored) with a scheme and a class name of a class that implements the StorageResolver
     * interface for that particular scheme.</p>
     *
     * @param configUrl URL of the configuration
     */
    public CaomArtifactResolver(URL configUrl) {
        URL url = configUrl;
        if (url == null) {
            log.debug("config URL is null: using default configuration.");
            url = CaomArtifactResolver.class.getClassLoader().getResource(CONFIG_FILENAME_DEFAULT);
        }

        try {
            this.defaultResolver = readConfig(url.openStream());
            log.debug("default resolver: " + defaultResolver);
        } catch (IOException ex) {
            throw new IllegalStateException("CONFIG: failed to read config from " + configUrl);
        }
    }
    
    private StorageResolver readConfig(InputStream istream) throws IOException {
        StorageResolver defResolver = null;
        Properties props = new Properties();
        props.load(istream);
        Iterator<String> i = props.stringPropertyNames().iterator();

        while (i.hasNext()) {
            String scheme = i.next();
            String cname = props.getProperty(scheme);
            try {
                log.debug("loading: " + cname);
                Class c = Class.forName(cname);
                log.debug("instantiating: " + c);
                StorageResolver handler = (StorageResolver) c.newInstance();

                if ("*".equals(scheme)) {
                    defResolver = handler;
                } else {
                    log.debug("adding: " + scheme + "," + handler);
                    handlers.put(scheme, handler);
                    log.debug("success: " + scheme + " is supported");
                }

            } catch (Exception fail) {
                throw new RuntimeException("CONFIG: failed to load " + cname, fail);
            }
        }
        // default
        setAuthMethod(AuthenticationUtil.getAuthMethod(AuthenticationUtil.getCurrentSubject()));
        return defResolver;
    }
    
    /**
     * Override the authentication method from the current subject in order to generate URLs
     * with a possibly different authentication method. This override may not be applicable
     * to all StorsgeResolver implementations.
     *
     * @param authMethod authentication method
     */
    public void setAuthMethod(AuthMethod authMethod) {
        this.authMethod = authMethod;
    }

    /**
     * Set RUNID value to optionally append to URLs. The runid is appended if the
     * StorageResolver implements the Traceable interface.
     *
     * @param runID RUNID value to optionally append to URLs
     */
    public void setRunID(String runID) {
        this.runID = runID;
    }

    private void setStorageResolverAuthMethod(final StorageResolver storageResolver) {
        try {
            final Class<?> clazz = storageResolver.getClass();
            final Method m = clazz.getMethod("setAuthMethod", AuthMethod.class);
            m.invoke(storageResolver, this.authMethod);
        } catch (NoSuchMethodException nsme) {
            log.debug(String.format("No setAuthMethod provided in %s", storageResolver.getClass().getSimpleName()));
        } catch (InvocationTargetException | IllegalAccessException classException) {
            throw new RuntimeException(String.format("Unable to set the auth method in class %s.",
                                                     storageResolver.getClass().getSimpleName()),
                                       classException);
        }
    }

    /**
     * Instantiate a StorageResolver based on the URI provided.
     * @param uri URI containing the scheme necessary to instantiate the resolver
     * @return A StorageResolver instance
     */
    public StorageResolver getStorageResolver(final URI uri) {
        StorageResolver ret = handlers.get(uri.getScheme());
        if (ret == null && !ResolverUtil.URL_SCHEMES.contains(uri.getScheme())) {
            ret = defaultResolver;
        }
        
        if (ret != null) {
            setStorageResolverAuthMethod(ret);
        }

        return ret;
    }

    /**
     * Find and call a suitable StorageResolver. This method gets the scheme from the
     * URI and uses it to find a configured StorageResolver. If that is successful, the
     * StorageResolver is used to do the conversion. If no StorageResolver can be found,
     * the URI.toURL() method is called as a fallback, which is sufficient to handle
     * URIs where the scheme is a known transport protocol (e.g. http).
     *
     * @param uri URI to transform.
     * @return a URL to the identified resource; null if the uri was null
     * @throws IllegalArgumentException      if a URL cannot be generated
     * @throws MalformedURLException    if a URL is not correctly generated
     */
    public URL getURL(URI uri)
        throws IllegalArgumentException, MalformedURLException {
        if (uri == null) {
            return null;
        }

        StorageResolver resolver = getStorageResolver(uri);
        if (resolver != null) {
            URL ret = resolver.toURL(uri);
            if (resolver instanceof Traceable) {
                ret = safeAppendRunID(ret);
            }
            return ret;
        }
        
        // fallback: hope for the best
        return uri.toURL();
    }

    /**
     * Add a new StorageResolver to the converter. If this handler has the same scheme as an
     * existing handler, it will replace the previous one.
     *
     * @param scheme URI scheme part
     * @param handler StorageResolver to be added.
     */
    public void addStorageResolver(String scheme, StorageResolver handler) {
        handlers.put(scheme, handler);
    }

    private URL safeAppendRunID(URL url) throws MalformedURLException {
        if (StringUtil.hasText(runID)) {
            StringBuilder sb = new StringBuilder();
            sb.append(url.toExternalForm());
            if (StringUtil.hasText(url.getQuery())) {
                sb.append("&");
            } else {
                sb.append("?");
            }
            sb.append("RUNID=").append(NetUtil.encode(runID));
            return new URL(sb.toString());
        }
        return url;
    }
}