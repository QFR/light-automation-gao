package up.light.wait;

public interface ICondition {
	boolean isTrue(String expect, String actual);
}
