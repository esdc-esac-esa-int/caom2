/*
 ************************************************************************
 *******************  CANADIAN ASTRONOMY DATA CENTRE  *******************
 **************  CENTRE CANADIEN DE DONN�ES ASTRONOMIQUES  **************
 *
 *  (c) 2020.                            (c) 2020.
 *  Government of Canada                 Gouvernement du Canada
 *  National Research Council            Conseil national de recherches
 *  Ottawa, Canada, K1A 0R6              Ottawa, Canada, K1A 0R6
 *  All rights reserved                  Tous droits r�serv�s
 *
 *  NRC disclaims any warranties,        Le CNRC d�nie toute garantie
 *  expressed, implied, or               �nonc�e, implicite ou l�gale,
 *  statutory, of any kind with          de quelque nature que ce
 *  respect to the software,             soit, concernant le logiciel,
 *  including without limitation         y compris sans restriction
 *  any warranty of merchantability      toute garantie de valeur
 *  or fitness for a particular          marchande ou de pertinence
 *  purpose. NRC shall not be            pour un usage particulier.
 *  liable in any event for any          Le CNRC ne pourra en aucun cas
 *  damages, whether direct or           �tre tenu responsable de tout
 *  indirect, special or general,        dommage, direct ou indirect,
 *  consequential or incidental,         particulier ou g�n�ral,
 *  arising from the use of the          accessoire ou fortuit, r�sultant
 *  software.  Neither the name          de l'utilisation du logiciel. Ni
 *  of the National Research             le nom du Conseil National de
 *  Council of Canada nor the            Recherches du Canada ni les noms
 *  names of its contributors may        de ses  participants ne peuvent
 *  be used to endorse or promote        �tre utilis�s pour approuver ou
 *  products derived from this           promouvoir les produits d�riv�s
 *  software without specific prior      de ce logiciel sans autorisation
 *  written permission.                  pr�alable et particuli�re
 *                                       par �crit.
 *
 *  This file is part of the             Ce fichier fait partie du projet
 *  OpenCADC project.                    OpenCADC.
 *
 *  OpenCADC is free software:           OpenCADC est un logiciel libre ;
 *  you can redistribute it and/or       vous pouvez le redistribuer ou le
 *  modify it under the terms of         modifier suivant les termes de
 *  the GNU Affero General Public        la ?GNU Affero General Public
 *  License as published by the          License? telle que publi�e
 *  Free Software Foundation,            par la Free Software Foundation
 *  either version 3 of the              : soit la version 3 de cette
 *  License, or (at your option)         licence, soit (� votre gr�)
 *  any later version.                   toute version ult�rieure.
 *
 *  OpenCADC is distributed in the       OpenCADC est distribu�
 *  hope that it will be useful,         dans l?espoir qu?il vous
 *  but WITHOUT ANY WARRANTY;            sera utile, mais SANS AUCUNE
 *  without even the implied             GARANTIE : sans m�me la garantie
 *  warranty of MERCHANTABILITY          implicite de COMMERCIALISABILIT�
 *  or FITNESS FOR A PARTICULAR          ni d?AD�QUATION � UN OBJECTIF
 *  PURPOSE.  See the GNU Affero         PARTICULIER. Consultez la Licence
 *  General Public License for           G�n�rale Publique GNU Affero
 *  more details.                        pour plus de d�tails.
 *
 *  You should have received             Vous devriez avoir re�u une
 *  a copy of the GNU Affero             copie de la Licence G�n�rale
 *  General Public License along         Publique GNU Affero avec
 *  with OpenCADC.  If not, see          OpenCADC ; si ce n?est
 *  <http://www.gnu.org/licenses/>.      pas le cas, consultez :
 *                                       <http://www.gnu.org/licenses/>.
 *
 *  $Revision: 5 $
 *
 ************************************************************************
 */

package ca.nrc.cadc.caom2.validator.collection;

import ca.nrc.cadc.date.DateUtil;
import java.net.URI;
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class ValidatorProgress {

    DateFormat df = DateUtil.getDateFormat(DateUtil.ISO_DATE_FORMAT, DateUtil.UTC);

    public String format(Date d) {
        if (d == null) {
            return "null";
        }
        return df.format(d);
    }

    public ValidatorProgress() {}

    // mutable
    public Date curLastModified;
    public UUID curID;
    public URI observationURI;

    // internal
    String source;

    public String getURIString() {
        if (observationURI != null) {
            return observationURI.toString();
        } else {
            // To be consistent with how the others are written to the file
            return "null";
        }
    }
    
    @Override
    public String toString() {
        return "observationURI: " + getURIString() + "\n"
            + "curLastModified: " + format(curLastModified) + "\n"
            + "curId: " + curID + "\n"
            + "source: " + source + "\n";
    }

}