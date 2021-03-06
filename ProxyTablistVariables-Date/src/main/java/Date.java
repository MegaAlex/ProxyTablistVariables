import eu.scrayos.proxytablist.api.Variable;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Date implements Variable {
    private final static Pattern pattern = Pattern.compile("\\{date:([\\w\\.\\s,:]+)\\}");
    private String dateFormat;

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public void setRefreshId(int refreshId) {

    }

    @Override
    public boolean hasUpdate(int slot, ProxiedPlayer proxiedPlayer) {
        return true;
    }

    @Override
    public void setMatchResult(MatchResult matchResult) {
        this.dateFormat = matchResult.group(1);
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(new java.util.Date());
    }
}
