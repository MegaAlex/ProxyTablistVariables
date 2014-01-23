import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Date implements Variable {
    private final static Pattern pattern = Pattern.compile("\\{date:([\\w\\.\\s,:]+)\\}");

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getText(String foundText, int refreshId, Short ping, ProxiedPlayer proxiedPlayer) {
        String dateFormat = foundText.substring(foundText.indexOf(":") + 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(new java.util.Date());
    }
}
