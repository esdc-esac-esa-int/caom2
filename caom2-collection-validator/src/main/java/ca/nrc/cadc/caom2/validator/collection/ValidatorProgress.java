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
