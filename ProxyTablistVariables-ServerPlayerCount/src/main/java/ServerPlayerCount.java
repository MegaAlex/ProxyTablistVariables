import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ServerPlayerCount implements Variable {
    private final static Pattern pattern = Pattern.compile("\\{serverCount:([\\w]+)\\}");
    private String server = null;

    private int players;

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public void setRefreshId(int i) {
    }

    @Override
    public boolean hasUpdate(int slot, ProxiedPlayer proxiedPlayer) {
        return true;
    }

    @Override
    public void setMatchResult(MatchResult matchResult) {
        server = matchResult.group(1);
        players = ProxyServer.getInstance().getServerInfo(server).getPlayers().size();
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    @Override
    public String getText() {
        return String.valueOf(players);
    }

    @Override
    public short getPing() {
        return 0;
    }
}
