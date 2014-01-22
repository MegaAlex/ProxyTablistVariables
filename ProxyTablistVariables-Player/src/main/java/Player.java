import eu.scrayos.proxytablist.objects.Variable;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Player implements Variable {
    private final static Pattern pattern = Pattern.compile("player");
    private int lastRefreshId = -1;
    private Iterator<ProxiedPlayer> playerIterator;

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public String getText(String foundString, int refreshId, Short ping) {
        if (lastRefreshId != refreshId) {
            lastRefreshId = refreshId;
            playerIterator = BungeeCord.getInstance().getPlayers().iterator();
        }

        if (!playerIterator.hasNext()) {
            return "";
        }

        ProxiedPlayer player = playerIterator.next();
        ping = (new Integer(player.getPing())).shortValue();

        return player.getName();
    }
}
