package up.light.repository;

import java.util.List;

import org.openqa.selenium.By;

/**
 * @version 1.0
 */
public class LocatorBean {
	private String in;
	private List<By> bys;

	public LocatorBean(String context, List<By> bys) {
		this.in = context;
		this.bys = bys;
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public List<By> getBys() {
		return bys;
	}

	public void setBys(List<By> bys) {
		this.bys = bys;
	}

	@Override
	public String toString() {
		return "LocatorBean [in=" + in + ", bys=" + bys + "]";
	}

}
