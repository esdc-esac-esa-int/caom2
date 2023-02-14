/*
************************************************************************
*******************  CANADIAN ASTRONOMY DATA CENTRE  *******************
**************  CENTRE CANADIEN DE DONNÉES ASTRONOMIQUES  **************
*
*  (c) 2011.                            (c) 2011.
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
package ca.nrc.cadc.fits2caom2.integration;

import javax.security.auth.Subject;
import ca.nrc.cadc.auth.SSLUtil;
import ca.nrc.cadc.caom2.fits.IngestableFile;
import ca.nrc.cadc.util.FileUtil;
import ca.nrc.cadc.util.Log4jInit;
import java.io.File;
import java.net.URI;
import java.security.PrivilegedAction;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jburke
 */
public class IngestableFileIntTest
{
    private static Logger log = Logger.getLogger(IngestableFileIntTest.class);
    
    private static final URI STORAGE_URI = URI.create("cadc:IRIS/I036B2H0.fits");

    public IngestableFileIntTest() { }

    @BeforeClass
    public static void setUpClass()
    {
        Log4jInit.setLevel("ca.nrc.cadc.caom2.fits", Level.DEBUG);
        Log4jInit.setLevel("ca.nrc.cadc.net", Level.DEBUG);
    }
    
    /**
     * Test of get method, of class IngestableFile.
     */
    @Test
    public void testGetFailure()
    {
        try
        {
            log.debug("testGetFailure");

            // case 1: get from ad without authentication
            URI uri = new URI("ad", "TEST/simple_fits", null);
            File localFile = null;
            IngestableFile ingestableFile = new IngestableFile(uri, localFile);
            try
            {
                ingestableFile.get();
                fail("Get from ad should fail without authentication");
            }
            catch (RuntimeException e)
            {
                log.debug(e.getMessage());
            }

            // case 2: get from vos without authentication
            uri = new URI("vos", "//cadc.nrc.ca~vospace/CADCRegtest1/DONOTDELETE_FITS2CAOM2_TESTFILES/private_file.txt", null);
            ingestableFile = new IngestableFile(uri, localFile);
            try
            {
                ingestableFile.get();
                fail("Get from vospace should fail without authentication");
            }
            catch (RuntimeException e)
            {
                log.debug(e.getMessage());
            }

            log.info("testGetFailure passed.");
        }
        catch (Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            Assert.fail("unexpected exception: " + unexpected);
        }
    }

    @Test
    public void testGetSuccessCADC()
    {
        try
        {
            log.debug("testGetSuccess");

            // case: get from ad without authenticating
            final URI uri = STORAGE_URI;
            File localFile = null;
            IngestableFile ingestableFile = new IngestableFile(uri, localFile);
            File file = null;
            try
            {
                file = ingestableFile.get();
            }
            catch (RuntimeException e)
            {
                fail("Get should not throw a runtime exception " + e.getMessage());
            }
            assertNotNull("File returned by Get should not be null", file);

            // case: get from ad with authentication.
            File certFile = FileUtil.getFileFromResource("fits2caom2.pem", VOSUriTest.class);
            Subject s = SSLUtil.createSubject(certFile);
            
            final IngestableFile ingestableFile2 = new IngestableFile(uri, localFile);
            file = null;
            try
            {
                file = (File) Subject.doAs(s, new PrivilegedAction<File>()
                    {
                        @SuppressWarnings("finally")
			public File run()
                        {
                            File file = null;
                            try 
                            {
                                file = ingestableFile2.get();
                            } 
                            catch (Exception e)
                            {
                                fail("Get should not throw an exception " + e.getMessage());
                            }
                            finally
                            {
                                return file;
                            }
                        }
                    });
            }
            catch (RuntimeException e)
            {
                fail("Get should not throw a runtime exception " + e.getMessage());
            }
            assertNotNull("File returned by Get should not be null", file);

        }
        catch (Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            Assert.fail("unexpected exception: " + unexpected);
        }
    }

    @Test
    public void testGetSuccessAD()
    {
        try
        {
            log.debug("testGetSuccess");

            // case: get from ad without authenticating
            final URI uri = new URI("ad", STORAGE_URI.getSchemeSpecificPart(), null);
            File localFile = null;
            IngestableFile ingestableFile = new IngestableFile(uri, localFile);
            File file = null;
            try
            {
                file = ingestableFile.get();
            }
            catch (RuntimeException e)
            {
                fail("Get should not throw a runtime exception " + e.getMessage());
            }
            assertNotNull("File returned by Get should not be null", file);

            // case: get from ad with authentication.
            File certFile = FileUtil.getFileFromResource("fits2caom2.pem", VOSUriTest.class);
            Subject s = SSLUtil.createSubject(certFile);
            
            final IngestableFile ingestableFile2 = new IngestableFile(uri, localFile);
            file = null;
            try
            {
                file = (File) Subject.doAs(s, new PrivilegedAction<File>()
                    {
                        @SuppressWarnings("finally")
			public File run()
                        {
                            File file = null;
                            try 
                            {
                                file = ingestableFile2.get();
                            } 
                            catch (Exception e)
                            {
                                fail("Get should not throw an exception " + e.getMessage());
                            }
                            finally
                            {
                                return file;
                            }
                        }
                    });
            }
            catch (RuntimeException e)
            {
                fail("Get should not throw a runtime exception " + e.getMessage());
            }
            assertNotNull("File returned by Get should not be null", file);

        }
        catch (Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            Assert.fail("unexpected exception: " + unexpected);
        }
    }

    @Test
    public void testGetSuccessVOS()
    {
        try
        {
            log.debug("testGetSuccess");

            // case: get from vos without authenticating
            URI uri = new URI("vos", "//cadc.nrc.ca~vault/CADCRegtest1/DONOTDELETE_FITS2CAOM2_TESTFILES/BLAST_250.fits", null);
            File localFile = null;
            IngestableFile ingestableFile = new IngestableFile(uri, localFile);
            File file = null;
            try
            {
                file = ingestableFile.get();
            }
            catch (Exception e)
            {
                fail("Get should not throw an exception " + e.getMessage());
            }
            assertNotNull("File returned by Get should not be null", file);

            // case: get from vos with authentication.
            File certFile = FileUtil.getFileFromResource("fits2caom2.pem", VOSUriTest.class);
            Subject s = SSLUtil.createSubject(certFile);
            
            uri = new URI("vos", "//cadc.nrc.ca~vault/CADCRegtest1/DONOTDELETE_FITS2CAOM2_TESTFILES/BLAST_250.fits", null);
            final IngestableFile ingestableFile3 = new IngestableFile(uri, localFile);
            file = null;
            try
            {
                file = (File) Subject.doAs(s, new PrivilegedAction<File>()
                    {
                        @SuppressWarnings("finally")
                        public File run()
                        {
                            File file = null;
                            try 
                            {
                                file = ingestableFile3.get();
                            } 
                            catch (Exception e)
                            {
                                fail("Get should not throw an exception " + e.getMessage());
                            }
                            finally
                            {
                                return file;
                            }
                        }
                    });
            }
            catch (RuntimeException e)
            {
                fail("Get should not throw a runtime exception " + e.getMessage());
            }

            assertNotNull("File returned by Get should not be null", file);
            log.info("testGetSuccess passed.");
        }
        catch (Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            Assert.fail("unexpected exception: " + unexpected);
        }
    }

}
