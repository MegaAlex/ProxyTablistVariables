import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ServerPlayerCount implements Variable {
    private final static Pattern pattern = Pattern.compile("\\{serverCount:([\\w]+)\\}");
    private String server = null;

    private int lastSlot = -1;

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public void setRefreshId(int i) {

    }

    @Override
    public boolean hasUpdate(int slot, ProxiedPlayer proxiedPlayer) {
        if(slot != lastSlot) {
            lastSlot = slot;

            return true;
        }

        return false;
    }

    @Override
    public void setMatchResult(MatchResult matchResult) {
        server = matchResult.group(1);
    }

    @Override
    public boolean isForGlobalTablist() {
        return true;
    }

    @Override
    public String getText(Short aShort) {
        ServerInfo serverInfo = BungeeCord.getInstance().getServerInfo(server);
        if(serverInfo == null) {
            return "Error";
        }

        return String.valueOf(serverInfo.getPlayers().size());
    }
}
