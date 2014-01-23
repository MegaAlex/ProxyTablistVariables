import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class OverAllCount implements Variable {
    private final static Pattern pattern = Pattern.compile("\\{overallCount\\}");

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getText(String foundString, int refreshId, Short ping, ProxiedPlayer proxiedPlayer) {
        return String.valueOf(BungeeCord.getInstance().getPlayers().size());
    }
}
