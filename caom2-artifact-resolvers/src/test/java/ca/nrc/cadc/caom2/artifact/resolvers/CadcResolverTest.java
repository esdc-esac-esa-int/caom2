/*
************************************************************************
*******************  CANADIAN ASTRONOMY DATA CENTRE  *******************
**************  CENTRE CANADIEN DE DONNÉES ASTRONOMIQUES  **************
*
*  (c) 2021.                            (c) 2021.
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
*  $Revision: 5 $
*
************************************************************************
*/

package ca.nrc.cadc.caom2.artifact.resolvers;

import ca.nrc.cadc.auth.AuthMethod;
import ca.nrc.cadc.caom2.artifact.resolvers.util.ResolverUtil;
import ca.nrc.cadc.net.Traceable;
import ca.nrc.cadc.util.Log4jInit;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author adriand
 */
public class CadcResolverTest {
    private static final Logger log = Logger.getLogger(CadcResolverTest.class);
    private static final String SI_URL = "https://unittest.com/global/files";


    static {
        Log4jInit.setLevel("ca.nrc.cadc", Level.INFO);
    }

    private static final String FILE_URI = "cadc:FOO/bar";

    private final CadcResolver cadcResolver;

    public CadcResolverTest() {
        cadcResolver = getCadcResolver();

    }

    private CadcResolver getCadcResolver() {
        // override the capabilities method
        return new CadcResolver() {
            @Override
            public URL getServiceURL(AuthMethod am) throws URISyntaxException {
                try {
                    return new URL(SI_URL);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Test
    public void testTraceable() {
        Assert.assertTrue(cadcResolver instanceof Traceable);
    }

    @Test
    public void testFileNoAuth() {
        try {
            URI uri = new URI(FILE_URI);
            URL url = cadcResolver.toURL(uri);
            Assert.assertNotNull(url);
            Assert.assertEquals(SI_URL + "/" + uri.toASCIIString(), url.toString());
            log.info("testFile: " + uri + " -> " + url);
        } catch (Exception unexpected) {
            log.error("unexpected exception", unexpected);
            Assert.fail("unexpected exception: " + unexpected);
        }
    }

    @Test
    public void testFileAnon() {
        try {
            cadcResolver.setAuthMethod(AuthMethod.ANON);
            URI uri = new URI(FILE_URI);
            URL url = cadcResolver.toURL(uri);
            Assert.assertNotNull(url);
            Assert.assertEquals(SI_URL + "/" + uri.toASCIIString(), url.toString());
            log.info("testFile: " + uri + " -> " + url);
        } catch (Exception unexpected) {
            log.error("unexpected exception", unexpected);
            Assert.fail("unexpected exception: " + unexpected);
        }
    }

    @Test
    public void testFileAuth() {
        try {
            cadcResolver.setAuthMethod(AuthMethod.CERT);
            URI uri = new URI(FILE_URI);
            URL url = cadcResolver.toURL(uri);
            Assert.assertNotNull(url);
            Assert.assertEquals(SI_URL + "/" + uri.toASCIIString(), url.toString());
            log.info("testFile: " + uri + " -> " + url);
        } catch (Exception unexpected) {
            log.error("unexpected exception", unexpected);
            Assert.fail("unexpected exception: " + unexpected);
        }
    }

    @Test
    public void testInvalidURIs() {
        // only "ad" and ResolverUtil.URL_SCHEMES are invalid
        try {
            URI uri = new URI("ad:GEM/N20201214S0014_th.jpg");
            URL url = cadcResolver.toURL(uri);
            Assert.fail("Exception expected");
        } catch (IllegalArgumentException expected) {
            log.info("expected exception: " + expected);
        } catch (Exception unexpected) {
            log.error("unexpected exception", unexpected);
            Assert.fail("unexpected exception: " + unexpected);
        }
        
        for (String s : ResolverUtil.URL_SCHEMES) {
            try {
                URI uri = new URI(s + ":://example.net/foo");
                URL url = cadcResolver.toURL(uri);
                Assert.fail("Exception expected");
            } catch (IllegalArgumentException expected) {
                log.info("expected exception: " + expected);
            } catch (Exception unexpected) {
                log.error("unexpected exception", unexpected);
                Assert.fail("unexpected exception: " + unexpected);
            }
        }
    }
}
