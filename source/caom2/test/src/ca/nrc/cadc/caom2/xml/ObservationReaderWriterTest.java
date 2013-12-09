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
package ca.nrc.cadc.caom2.xml;

import ca.nrc.cadc.caom2.Artifact;
import ca.nrc.cadc.caom2.Chunk;
import ca.nrc.cadc.caom2.CompositeObservation;
import ca.nrc.cadc.caom2.Environment;
import ca.nrc.cadc.caom2.Instrument;
import ca.nrc.cadc.caom2.Observation;
import ca.nrc.cadc.caom2.ObservationURI;
import ca.nrc.cadc.caom2.Part;
import ca.nrc.cadc.caom2.Plane;
import ca.nrc.cadc.caom2.PlaneURI;
import ca.nrc.cadc.caom2.Proposal;
import ca.nrc.cadc.caom2.Provenance;
import ca.nrc.cadc.caom2.SimpleObservation;
import ca.nrc.cadc.caom2.Target;
import ca.nrc.cadc.caom2.Telescope;
import ca.nrc.cadc.caom2.wcs.Axis;
import ca.nrc.cadc.caom2.wcs.Coord2D;
import ca.nrc.cadc.caom2.wcs.CoordAxis1D;
import ca.nrc.cadc.caom2.wcs.CoordAxis2D;
import ca.nrc.cadc.caom2.wcs.CoordBounds1D;
import ca.nrc.cadc.caom2.wcs.CoordBounds2D;
import ca.nrc.cadc.caom2.wcs.CoordCircle2D;
import ca.nrc.cadc.caom2.wcs.CoordError;
import ca.nrc.cadc.caom2.wcs.CoordFunction1D;
import ca.nrc.cadc.caom2.wcs.CoordFunction2D;
import ca.nrc.cadc.caom2.wcs.CoordPolygon2D;
import ca.nrc.cadc.caom2.wcs.CoordRange1D;
import ca.nrc.cadc.caom2.wcs.CoordRange2D;
import ca.nrc.cadc.caom2.wcs.Dimension2D;
import ca.nrc.cadc.caom2.wcs.ObservableAxis;
import ca.nrc.cadc.caom2.wcs.PolarizationWCS;
import ca.nrc.cadc.caom2.wcs.RefCoord;
import ca.nrc.cadc.caom2.wcs.Slice;
import ca.nrc.cadc.caom2.wcs.SpatialWCS;
import ca.nrc.cadc.caom2.wcs.SpectralWCS;
import ca.nrc.cadc.caom2.wcs.TemporalWCS;
import ca.nrc.cadc.util.Log4jInit;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.Set;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author jburke
 */
public class ObservationReaderWriterTest
{
    private static Logger log = Logger.getLogger(ObservationReaderWriterTest.class);
    static
    {
        Log4jInit.setLevel("ca.nrc.cadc.caom2.xml", Level.INFO);
    }
    
    public ObservationReaderWriterTest() { }

    //@Test
    public void testTemplate()
    {
        try
        {
            
        }
        catch(Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            fail("unexpected exception: " + unexpected);
        }
    }
    
    @Test
    public void testReadNull()
    {
        try
        {
            ObservationReader r = new ObservationReader();
            
            try { r.read((String) null); }
            catch(IllegalArgumentException expected) { }
            
            try { r.read((InputStream) null); }
            catch(IllegalArgumentException expected) { }
            
            try { r.read((Reader) null); }
            catch(IllegalArgumentException expected) { }
        }
        catch(Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            fail("unexpected exception: " + unexpected);
        }
    }
    
    @Test
    public void testMinimalSimple()
    {
        try
        {
            log.debug("testMinimalSimple");
            for (int i = 1; i < 6; i++)
            {
                // CoordBounds2D as CoordCircle2D
                boolean boundsIsCircle = true;
                SimpleObservation observation = getMinimalSimple(i, boundsIsCircle);
                
                // Write empty elements.
                testObservation(observation, true);
                
                // Do not write empty elements.
                testObservation(observation, false);
                
                // CoordBounds2D as CoordPolygon2D
                boundsIsCircle = false;
                observation = getMinimalSimple(i, boundsIsCircle);
                
                // Write empty elements.
                testObservation(observation, true);
                
                // Do not write empty elements.
                testObservation(observation, false);
            }
        }
        catch(Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            fail("unexpected exception: " + unexpected);
        }
    }
    
    @Test
    public void testCompleteSimple()
    {
        try
        {
            log.debug("testCompleteSimple");
            for (int i = 1; i < 6; i++)
            {
                // CoordBounds2D as CoordCircle2D
                boolean boundsIsCircle = true;
                SimpleObservation observation = getCompleteSimple(i, boundsIsCircle);
                
                // Write empty elements.
                testObservation(observation, true);
                
                // Do not write empty elements.
                testObservation(observation, false);
                
                // CoordBounds2D as CoordPolygon2D
                boundsIsCircle = false;
                observation = getCompleteSimple(i, boundsIsCircle);
                
                // Write empty elements.
                testObservation(observation, true);
                
                // Do not write empty elements.
                testObservation(observation, false);
            }

            SimpleObservation observation = getCompleteSimple(5, true);
            testObservation(observation, false, true, "c2"); // custom ns prefix

        }
        catch(Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            fail("unexpected exception: " + unexpected);
        }
    }
    
    @Test
    public void testMinimalComposite()
    {
        try
        {
            log.debug("testMinimalComposite");
            for (int i = 1; i < 6; i++)
            {
                // CoordBounds2D as CoordCircle2D
                boolean boundsIsCircle = true;
                CompositeObservation observation = getMinimalComposite(i, boundsIsCircle);
                
                // Write empty elements.
                testObservation(observation, true);
                
                // Do not write empty elements.
                testObservation(observation, false);
                
                // CoordBounds2D as CoordPolygon2D
                boundsIsCircle = false;
                observation = getMinimalComposite(i, boundsIsCircle);
                
                // Write empty elements.
                testObservation(observation, true);
                
                // Do not write empty elements.
                testObservation(observation, false);
            }
        }
        catch(Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            fail("unexpected exception: " + unexpected);
        }
    }
    
    @Test
    public void testCompleteComposite()
    {
        try
        {
            log.debug("testCompleteComposite");
            for (int i = 1; i < 6; i++)
            {
                // CoordBounds2D as CoordCircle2D
                boolean boundsIsCircle = true;
                CompositeObservation observation = getCompleteComposite(i, boundsIsCircle);
                
                // Write empty elements.
                testObservation(observation, true);
                
                // Do not write empty elements.
                testObservation(observation, false);
                
                // CoordBounds2D as CoordPolygon2D
                boundsIsCircle = false;
                observation = getCompleteComposite(i, boundsIsCircle);
                
                // Write empty elements.
                testObservation(observation, true);
                
                // Do not write empty elements.
                testObservation(observation, false);
            }
        }
        catch(Exception unexpected)
        {
            log.error("unexpected exception", unexpected);
            fail("unexpected exception: " + unexpected);
        }
    }
    
    protected SimpleObservation getMinimalSimple(int depth, boolean boundsIsCircle)
        throws Exception
    {        
        Caom2TestInstances instances = new Caom2TestInstances();
        instances.setComplete(false);
        instances.setDepth(depth);
        instances.setBoundsIsCircle(boundsIsCircle);
        return instances.getSimpleObservation();
    }
    
    protected SimpleObservation getCompleteSimple(int depth, boolean boundsIsCircle)
        throws Exception
    {        
        Caom2TestInstances instances = new Caom2TestInstances();
        instances.setComplete(true);
        instances.setDepth(depth);
        instances.setBoundsIsCircle(boundsIsCircle);
        return instances.getSimpleObservation();
    }
    
    protected CompositeObservation getMinimalComposite(int depth, boolean boundsIsCircle)
        throws Exception
    {
        Caom2TestInstances instances = new Caom2TestInstances();
        instances.setComplete(false);
        instances.setDepth(depth);
        instances.setBoundsIsCircle(boundsIsCircle);
        return instances.getCompositeObservation();
    }
    
    protected CompositeObservation getCompleteComposite(int depth, boolean boundsIsCircle)
        throws Exception
    {
        Caom2TestInstances instances = new Caom2TestInstances();
        instances.setComplete(true);
        instances.setDepth(depth);
        instances.setBoundsIsCircle(boundsIsCircle);
        return instances.getCompositeObservation();
    }

    protected void testObservation(Observation observation, boolean writeEmptyCollections)
        throws Exception
    {
        testObservation(observation, writeEmptyCollections, false, null);
    }

    protected void testObservation(Observation observation, boolean writeEmptyCollections, boolean customPrefix, String nsPrefix)
        throws Exception
    {
        StringBuilder sb = new StringBuilder();
        ObservationWriter writer = new ObservationWriter();
        if (customPrefix)
            writer = new ObservationWriter(nsPrefix, writeEmptyCollections);
        writer.setWriteEmptyCollections(writeEmptyCollections);
        writer.write(observation, sb);
        log.debug(sb.toString());

        // do not validate the XML.
        ObservationReader reader = new ObservationReader(false);
        Observation returned = reader.read(sb.toString());

        compareObservations(observation, returned);
        
        // validate the XML.
        reader = new ObservationReader(true);
        returned = reader.read(sb.toString());

        compareObservations(observation, returned);
    }
    
    protected void compareObservations(Observation expected, Observation actual)
    {
        assertEquals("obs type", expected.getClass().getName(), actual.getClass().getName());

        assertEquals(expected.getID(), actual.getID());
        if (expected.getLastModified() != null && actual.getLastModified() != null)
            assertEquals("Observation.lastModified", expected.getLastModified().getTime(), actual.getLastModified().getTime());
    
        assertNotNull(expected.getCollection());
        assertNotNull(actual.getCollection());
        assertEquals(expected.getCollection(), actual.getCollection());
        
        assertNotNull(expected.getObservationID());
        assertNotNull(actual.getObservationID());
        assertEquals(expected.getObservationID(), actual.getObservationID());
        
        assertNotNull(expected.getAlgorithm());
        assertNotNull(actual.getAlgorithm());
        assertEquals(expected.getAlgorithm().getName(), actual.getAlgorithm().getName());
        
        assertEquals(expected.metaRelease, actual.metaRelease);
        compareProposal(expected.proposal, actual.proposal);
        compareTarget(expected.target, actual.target);
        compareTelescope(expected.telescope, actual.telescope);
        compareInstrument(expected.instrument, actual.instrument);
        compareEnvironment(expected.environment, actual.environment);
        
        comparePlanes(expected.getPlanes(), actual.getPlanes());
        
        if (expected instanceof CompositeObservation && actual instanceof CompositeObservation)
        {
            compareMembers(((CompositeObservation) expected).getMembers(), ((CompositeObservation) actual).getMembers());
        }
    }
    
    protected void compareProposal(Proposal expected, Proposal actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getID(), actual.getID());
        assertEquals(expected.pi, actual.pi);
        assertEquals(expected.project, actual.project);
        assertEquals(expected.title, actual.title);
        assertEquals(expected.getKeywords().size(), actual.getKeywords().size());
        
        int size = expected.getKeywords().size();
        for (int i = 0; i < size; i++)
        {
            assertEquals(expected.getKeywords().get(i), actual.getKeywords().get(i));
        }
    }
    
    protected void compareTarget(Target expected, Target actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.type, actual.type);
        assertEquals(expected.redshift, actual.redshift);
        assertEquals(expected.getKeywords().size(), actual.getKeywords().size());
        
        int size = expected.getKeywords().size();
        for (int i = 0; i < size; i++)
        {
            assertEquals(expected.getKeywords().get(i), actual.getKeywords().get(i));
        }
    }
    
    protected void compareTelescope(Telescope expected, Telescope actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.geoLocationX, actual.geoLocationX);
        assertEquals(expected.geoLocationY, actual.geoLocationY);
        assertEquals(expected.geoLocationZ, actual.geoLocationZ);
        assertEquals(expected.getKeywords().size(), actual.getKeywords().size());
        
        int size = expected.getKeywords().size();
        for (int i = 0; i < size; i++)
        {
            assertEquals(expected.getKeywords().get(i), actual.getKeywords().get(i));
        }
    }
    
    protected void compareInstrument(Instrument expected, Instrument actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getKeywords().size(), actual.getKeywords().size());
        
        int size = expected.getKeywords().size();
        for (int i = 0; i < size; i++)
        {
            assertEquals(expected.getKeywords().get(i), actual.getKeywords().get(i));
        }
    }

    protected void compareEnvironment(Environment expected, Environment actual)
    {
        if (expected == null)
        {
            assertNull(actual);
            return;
        }

        assertNotNull(actual);
        assertEquals(expected.seeing, actual.seeing, 0.0);
        assertEquals(expected.humidity, actual.humidity, 0.0);
        assertEquals(expected.elevation, actual.elevation, 0.0);
        assertEquals(expected.tau, actual.tau, 0.0);
        assertEquals(expected.wavelengthTau, actual.wavelengthTau, 0.0);
        assertEquals(expected.ambientTemp, actual.ambientTemp, 0.0);
        assertEquals(expected.photometric, actual.photometric);

    }
    
    protected void compareMembers(Set<ObservationURI> expected, Set<ObservationURI> actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        
        Iterator actualIter = expected.iterator();
        Iterator expectedIter = actual.iterator();
        while (expectedIter.hasNext())
        {
            ObservationURI expectedUri = (ObservationURI) expectedIter.next();
            ObservationURI actualUri = (ObservationURI) actualIter.next();
            
            assertNotNull(expectedUri);
            assertNotNull(actualUri);
            assertEquals(expectedUri, actualUri);
        }
    }
    
    protected void comparePlanes(Set<Plane> expected, Set<Plane> actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        
        Iterator actualIter = expected.iterator();
        Iterator expectedIter = actual.iterator();
        while (expectedIter.hasNext())
        {
            Plane expectedPlane = (Plane) expectedIter.next();
            Plane actualPlane = (Plane) actualIter.next();
            
            assertNotNull(expectedPlane);
            assertNotNull(actualPlane);

            assertEquals(expectedPlane.getID(), actualPlane.getID());
            if (expectedPlane.getLastModified() != null && actualPlane.getLastModified() != null)
                assertEquals("Plane.lastModified", expectedPlane.getLastModified().getTime(), actualPlane.getLastModified().getTime());

            assertEquals(expectedPlane.getProductID(), actualPlane.getProductID());
            assertEquals(expectedPlane.metaRelease, actualPlane.metaRelease);
            assertEquals(expectedPlane.dataRelease, actualPlane.dataRelease);
            assertEquals(expectedPlane.dataProductType, actualPlane.dataProductType);
            assertEquals(expectedPlane.calibrationLevel, actualPlane.calibrationLevel);
            
            compareProvenance(expectedPlane.provenance, actualPlane.provenance);
            compareArtifacts(expectedPlane.getArtifacts(), actualPlane.getArtifacts());
        }
    }
    
    protected void compareProvenance(Provenance expected, Provenance actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertEquals(expected.version, actual.version);
        assertEquals(expected.project, actual.project);
        assertEquals(expected.producer, actual.producer);
        assertEquals(expected.runID, actual.runID);
        assertEquals(expected.reference, actual.reference);
        assertEquals(expected.lastExecuted, actual.lastExecuted);
        compareInputs(expected.getInputs(), actual.getInputs());
    }
    
    protected void compareInputs(Set<PlaneURI> expected, Set<PlaneURI> actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        
        Iterator actualIter = expected.iterator();
        Iterator expectedIter = actual.iterator();
        while (expectedIter.hasNext())
        {
            PlaneURI expectedPlaneUri = (PlaneURI) expectedIter.next();
            PlaneURI actualPlaneUri = (PlaneURI) actualIter.next();
            
            assertNotNull(expectedPlaneUri);
            assertNotNull(actualPlaneUri);
            assertEquals(expectedPlaneUri, actualPlaneUri);
        }
    }
    
    protected void compareArtifacts(Set<Artifact> expected, Set<Artifact> actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        
        Iterator actualIter = expected.iterator();
        Iterator expectedIter = actual.iterator();
        while (expectedIter.hasNext())
        {
            Artifact expectedArtifact = (Artifact) expectedIter.next();
            Artifact actualArtifact = (Artifact) actualIter.next();
            
            assertNotNull(expectedArtifact);
            assertNotNull(actualArtifact);

            assertEquals(expectedArtifact.getID(), actualArtifact.getID());
            if (expectedArtifact.getLastModified() != null && actualArtifact.getLastModified() != null)
                assertEquals("Artifact.lastModified", expectedArtifact.getLastModified().getTime(), actualArtifact.getLastModified().getTime());
            
            assertEquals(expectedArtifact.getURI(), actualArtifact.getURI());
            assertEquals(expectedArtifact.contentType, actualArtifact.contentType);
            assertEquals(expectedArtifact.contentLength, actualArtifact.contentLength);
            assertEquals(expectedArtifact.productType, actualArtifact.productType);
            assertEquals(expectedArtifact.alternative, actualArtifact.alternative);
            
            compareParts(expectedArtifact.getParts(), expectedArtifact.getParts());
        }
    }
    
    protected void compareParts(Set<Part> expected, Set<Part> actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        
        Iterator actualIter = expected.iterator();
        Iterator expectedIter = actual.iterator();
        while (expectedIter.hasNext())
        {
            Part expectedPart = (Part) expectedIter.next();
            Part actualPart = (Part) actualIter.next();
            
            assertNotNull(expectedPart);
            assertNotNull(actualPart);

            assertEquals(expectedPart.getID(), actualPart.getID());
            if (expectedPart.getLastModified() != null && actualPart.getLastModified() != null)
                assertEquals("Part.lastModified", expectedPart.getLastModified().getTime(), actualPart.getLastModified().getTime());
            
            assertEquals(expectedPart.getName(), actualPart.getName());
            assertEquals(expectedPart.productType, actualPart.productType);
            
            compareChunks(expectedPart.getChunks(), actualPart.getChunks());
        }
    }
    
    protected void compareChunks(Set<Chunk> expected, Set<Chunk> actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        
        Iterator actualIter = expected.iterator();
        Iterator expectedIter = actual.iterator();
        while (expectedIter.hasNext())
        {
            Chunk expectedChunk = (Chunk) expectedIter.next();
            Chunk actualChunk = (Chunk) actualIter.next();
            
            assertNotNull(expectedChunk);
            assertNotNull(actualChunk);

            assertEquals(expectedChunk.getID(), actualChunk.getID());
            if (expectedChunk.getLastModified() != null && actualChunk.getLastModified() != null)
                assertEquals("Chunk.lastModified", expectedChunk.getLastModified().getTime(), actualChunk.getLastModified().getTime());
            
            assertEquals(expectedChunk.productType, actualChunk.productType);
            assertEquals(expectedChunk.naxis, actualChunk.naxis);
            assertEquals(expectedChunk.observableAxis, actualChunk.observableAxis);
            assertEquals(expectedChunk.positionAxis1, actualChunk.positionAxis1);
            assertEquals(expectedChunk.positionAxis2, actualChunk.positionAxis2);
            assertEquals(expectedChunk.energyAxis, actualChunk.energyAxis);
            assertEquals(expectedChunk.timeAxis, actualChunk.timeAxis);
            assertEquals(expectedChunk.polarizationAxis, actualChunk.polarizationAxis);
            
            compareObservableAxis(expectedChunk.observable, actualChunk.observable);
            compareSpatialWCS(expectedChunk.position, actualChunk.position);
            compareSpectralWCS(expectedChunk.energy, actualChunk.energy);
            compareTemporalWCS(expectedChunk.time, actualChunk.time);
            comparePolarizationWCS(expectedChunk.polarization, actualChunk.polarization);
        }
    }
    
    protected void compareObservableAxis(ObservableAxis expected, ObservableAxis actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        
        compareSlice(expected.getDependent(), actual.getDependent());
        compareSlice(expected.independent, actual.independent);
    }
    
    protected void compareSpatialWCS(SpatialWCS expected, SpatialWCS actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        
        compareCoordAxis2D(expected.getAxis(), actual.getAxis());
        assertEquals(expected.coordsys, actual.coordsys);
        assertEquals(expected.equinox, actual.equinox);
        assertEquals(expected.resolution, actual.resolution);
    }
    
    protected void compareSpectralWCS(SpectralWCS expected, SpectralWCS actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        
        compareCoordAxis1D(expected.getAxis(), actual.getAxis());
        assertEquals(expected.bandpassName, actual.bandpassName);
        assertEquals(expected.resolvingPower, actual.resolvingPower);
        assertEquals(expected.restfrq, actual.restfrq);
        assertEquals(expected.restwav, actual.restwav);
        assertEquals(expected.getSpecsys(), actual.getSpecsys());
        assertEquals(expected.ssysobs, actual.ssysobs);
        assertEquals(expected.ssyssrc, actual.ssyssrc);
        assertEquals(expected.velang, actual.velang);
        assertEquals(expected.velosys, actual.velosys);
        assertEquals(expected.zsource, actual.zsource);
    }
    
    protected void compareTemporalWCS(TemporalWCS expected, TemporalWCS actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        
        compareCoordAxis1D(expected.getAxis(), actual.getAxis());
        assertEquals(expected.exposure, actual.exposure);
        assertEquals(expected.resolution, actual.resolution);
    }
    
    protected void comparePolarizationWCS(PolarizationWCS expected, PolarizationWCS actual)
    {
        if (expected == null && actual == null)
            return;
        
        assertNotNull(expected);
        assertNotNull(actual);
        
        compareCoordAxis1D(expected.getAxis(), actual.getAxis());
    }
    
    protected void compareAxis(Axis expected, Axis actual)
    {        
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertNotNull(actual.getCtype());
        assertNotNull(actual.getCunit());
        
        assertEquals(expected.getCtype(), actual.getCtype());
        assertEquals(expected.getCunit(), actual.getCunit());
    }
    
    protected void compareCoord2D(Coord2D expected, Coord2D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        compareRefCoord(expected.getCoord1(), actual.getCoord1());
        compareRefCoord(expected.getCoord2(), actual.getCoord2());
    }
    
    protected void compareCoordAxis1D(CoordAxis1D expected, CoordAxis1D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        compareCoordError(expected.error, actual.error);
        compareCoordRange1D(expected.range, actual.range);
        compareCoordBounds1D(expected.bounds, actual.bounds);
        compareCoordFunction1D(expected.function, actual.function);
    }
    
    protected void compareCoordAxis2D(CoordAxis2D expected, CoordAxis2D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);

        assertNotNull(actual.getAxis1());
        assertNotNull(actual.getAxis2());
        
        compareAxis(expected.getAxis1(), actual.getAxis1());
        compareAxis(expected.getAxis2(), actual.getAxis2());
        compareCoordError(expected.error1, actual.error1);
        compareCoordError(expected.error2, actual.error2);
        compareCoordRange2D(expected.range, actual.range);
        compareCoordBounds2D(expected.bounds, actual.bounds);
        compareCoordFunction2D(expected.function, actual.function);
    }
    
    protected void compareCoordBounds1D(CoordBounds1D expected, CoordBounds1D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertNotNull(expected.getSamples());
        assertNotNull(actual.getSamples());
        assertEquals(expected.getSamples().size(), actual.getSamples().size());
        
        Iterator actualIter = expected.getSamples().iterator();
        Iterator expectedIter = actual.getSamples().iterator();
        while (expectedIter.hasNext())
        {
            CoordRange1D expectedRange = (CoordRange1D) expectedIter.next();
            CoordRange1D actualRange = (CoordRange1D) actualIter.next();
            compareCoordRange1D(expectedRange, actualRange);
        }
    }
    
    protected void compareCoordBounds2D(CoordBounds2D expected, CoordBounds2D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        if (expected instanceof CoordCircle2D && actual instanceof CoordCircle2D)
            compareCoordCircle2D((CoordCircle2D) expected, (CoordCircle2D) actual);
        else if (expected instanceof CoordPolygon2D && actual instanceof CoordPolygon2D)
            compareCoordPolygon2D((CoordPolygon2D) expected, (CoordPolygon2D) actual);
        else
            fail("CoordBounds2D expected and actual are different types.");
                
    }
    
    protected void compareCoordCircle2D(CoordCircle2D expected, CoordCircle2D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertNotNull(actual.getCenter());
        assertNotNull(actual.getRadius());
        compareCoord2D(expected.getCenter(), actual.getCenter());
        assertEquals(expected.getRadius(), actual.getRadius());
    }
    
    protected void compareCoordError(CoordError expected, CoordError actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertNotNull(actual.syser);
        assertNotNull(actual.rnder);
        
        assertEquals(expected.syser, actual.syser);
        assertEquals(expected.rnder, actual.rnder);
    }
    
    protected void compareCoordFunction1D(CoordFunction1D expected, CoordFunction1D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertEquals(expected.getNaxis(), actual.getNaxis());
        assertEquals(expected.getDelta(), actual.getDelta());
        compareRefCoord(expected.getRefCoord(), actual.getRefCoord());
    }
    
    protected void compareCoordFunction2D(CoordFunction2D expected, CoordFunction2D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertNotNull(actual.getDimension());
        assertNotNull(actual.getRefCoord());
        assertNotNull(actual.getCd11());
        assertNotNull(actual.getCd12());
        assertNotNull(actual.getCd21());
        assertNotNull(actual.getCd22());
        
        compareDimension2D(expected.getDimension(), actual.getDimension());
        compareCoord2D(expected.getRefCoord(), actual.getRefCoord());
        assertEquals(expected.getCd11(), actual.getCd11(), 0.0);
        assertEquals(expected.getCd12(), actual.getCd12(), 0.0);
        assertEquals(expected.getCd21(), actual.getCd21(), 0.0);
        assertEquals(expected.getCd22(), actual.getCd22(), 0.0);
    }
    
    protected void compareCoordPolygon2D(CoordPolygon2D expected, CoordPolygon2D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertNotNull(expected.getVertices());
        assertNotNull(actual.getVertices());
        assertEquals(expected.getVertices().size(), actual.getVertices().size());
        
        Iterator actualIter = expected.getVertices().iterator();
        Iterator expectedIter = actual.getVertices().iterator();
        while (expectedIter.hasNext())
        {
            Coord2D expectedCoord2D = (Coord2D) expectedIter.next();
            Coord2D actualCoord2D = (Coord2D) actualIter.next();
            compareCoord2D(expectedCoord2D, actualCoord2D);
        }
    }
    
    protected void compareCoordRange1D(CoordRange1D expected, CoordRange1D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        compareRefCoord(expected.getStart(), actual.getStart());
        compareRefCoord(expected.getEnd(), actual.getEnd());
    }
    
    protected void compareCoordRange2D(CoordRange2D expected, CoordRange2D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertNotNull(actual.getStart());
        assertNotNull(actual.getEnd());
        
        compareCoord2D(expected.getStart(), actual.getStart());
        compareCoord2D(expected.getEnd(), actual.getEnd());
    }
    
    protected void compareDimension2D(Dimension2D expected, Dimension2D actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.naxis1, actual.naxis1);
        assertEquals(expected.naxis2, actual.naxis2);
    }
    
    protected void compareRefCoord(RefCoord expected, RefCoord actual)
    {
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertEquals(expected.pix, actual.pix, 0.0);
        assertEquals(expected.val, actual.val, 0.0);
    }
    
    protected void compareSlice(Slice expected, Slice actual)
    {        
        assertNotNull(expected);
        assertNotNull(actual);
        
        assertNotNull(actual.getBin());
        assertNotNull(actual.getAxis());
        
        assertEquals(expected.getBin(), actual.getBin());
        compareAxis(expected.getAxis(), actual.getAxis());
    }

}