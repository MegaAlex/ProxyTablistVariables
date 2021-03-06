import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Iterator;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Player implements Variable {
    private final static Pattern pattern = Pattern.compile("\\{player\\}");
    private Iterator<ProxiedPlayer> playerIterator;
    private ProxiedPlayer proxiedPlayer;

    private int lastRefreshId = -1;
    private int lastSlot;

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public void setRefreshId(int refreshId) {
        if(lastRefreshId != refreshId) {
            lastRefreshId = refreshId;

            playerIterator = BungeeCord.getInstance().getPlayers().iterator();
        }
    }

    @Override
    public boolean hasUpdate(int slot, ProxiedPlayer proxiedPlayer) {
        if(slot != lastSlot) {
            lastSlot = slot;
            this.proxiedPlayer = (playerIterator.hasNext()) ? playerIterator.next() : null;

            return true;
        }

        return false;
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
        return (proxiedPlayer != null) ? (short) proxiedPlayer.getPing() : 0;
    }

    @Override
    public String getText() {

        if(proxiedPlayer != null) {
            return formatName(this.proxiedPlayer);
        }

        return "";
    }

    public String formatName(ProxiedPlayer p) {
        StringBuilder name = new StringBuilder();

        for (String c : new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "l", "m", "n", "o", "k", "r"}) {
            if (p.hasPermission("proxy.tablist." + c)) {
                name.append("§");
                name.append(c);
            }
        }
        
        name.append(p.getName());
        return name.toString();
    }
}
