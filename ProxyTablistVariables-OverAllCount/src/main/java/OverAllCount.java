import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.regex.MatchResult;
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
    public void setRefreshId(int i) {

    }

    @Override
    public boolean hasUpdate(int i, ProxiedPlayer proxiedPlayer) {
        return true;
    }

    @Override
    public void setMatchResult(MatchResult matchResult) {

    }

    @Override
    public boolean isGlobal() {
        return true;
    }

    @Override
    public short getPing() {
        return 0;
    }

    @Override
    public String getText() {
        return String.valueOf(BungeeCord.getInstance().getPlayers().size());
    }
}
