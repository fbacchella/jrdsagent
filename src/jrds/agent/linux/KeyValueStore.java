package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jrds.agent.LProbe;

public class KeyValueStore implements LProbe {
	private String pattern = "^(\\w+)[: ]+(.*)$";
	private String path = "/dev/null";
	private boolean isSI = true;
	private static final Pattern unitPattern = Pattern.compile("(\\d+)( *([k])[Bb])?");

	public KeyValueStore(String path) {
		super();
		this.path = path;
	}

	public KeyValueStore(String pattern, String path) {
		super();
		this.pattern = pattern;
		this.path = path;
	}

	public String getName() throws RemoteException {
		return "KeyValueStore";
	}

	@SuppressWarnings("unchecked")
	public Map query() throws RemoteException {
		try {
			return parse();
		} catch (IOException e) {
			throw new RemoteException(getName(), e);
		}
	}

	private Map<String, Number> parse() throws IOException {
		File stat = new File(path);
		BufferedReader r = new BufferedReader(new FileReader(stat));
		Map<String, Number> retValue = new HashMap<String, Number>();
		Pattern p = Pattern.compile(pattern); 
		String line;
		while(( line = r.readLine()) != null) {
			Matcher m = p.matcher(line);
			if(m.matches()) {
				String key = m.group(1);
				String value = m.group(2);
				Matcher unitMatcher = unitPattern.matcher(value);
				if(unitMatcher.matches()) {
					Number n = jrds.Util.parseStringNumber(unitMatcher.group(1), Double.class, 0);
					if(unitMatcher.groupCount() == 3) {
						String unit = unitMatcher.group(3);
						jrds.Util.SiPrefix sp = jrds.Util.SiPrefix.valueOf(unit);
						n = sp.evaluate(n.doubleValue(), isSI);
					}
					retValue.put(key, n);
				}
			}
		}
		return retValue;
	}
}
