import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ServerPlayerCount implements Variable {
    private final static Pattern pattern = Pattern.compile("\\{serverCount:([\\w]+)\\}");

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getText(String foundString, int refreshId, Short ping, ProxiedPlayer proxiedPlayer, Boolean global) {
        String server = pattern.matcher(foundString).group(1);

        ServerInfo serverInfo = BungeeCord.getInstance().getServerInfo(server);
        if(serverInfo == null) {
            return "Error";
        }

        return String.valueOf(serverInfo.getPlayers().size());
    }
}
