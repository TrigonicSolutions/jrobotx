package net.sourceforge.jrobotx.validation;

public class StandardValidationSuite extends ValidatorCollection {
	private static final long serialVersionUID = 1L;

	public StandardValidationSuite() {
		add(new IgnoredFieldValidator());
		add(new DuplicateUserAgentValidator());
	}
}
