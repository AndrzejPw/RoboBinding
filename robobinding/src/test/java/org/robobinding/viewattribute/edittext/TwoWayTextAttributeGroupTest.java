package org.robobinding.viewattribute.edittext;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.robobinding.viewattribute.RandomValues.either;
import static org.robobinding.viewattribute.edittext.TwoWayTextAttributeGroup.TEXT;

import org.junit.Test;
import org.robobinding.attribute.MalformedAttributeException;
import org.robobinding.attribute.MissingRequiredAttributesException;
import org.robobinding.viewattribute.AbstractGroupedViewAttributeTest;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TwoWayTextAttributeGroupTest extends AbstractGroupedViewAttributeTest<TwoWayTextAttributeGroup> {
    private final Attribute oneWayBindingText = attribute("text={name}");
    private final Attribute twoWayBindingText = attribute("text=${name}");
    private final Attribute valueCommitMode = attribute("valueCommitMode=onChange");

    @Test
    public void givenATextAttribute_thenCreateInstance() {
	givenAttribute(either(oneWayBindingText, twoWayBindingText));

	performInitialization();

	assertThatAttributeWasCreated(TwoWayTextAttribute.class);
    }

    @Test
    public void givenATextAttribute_thenValueCommitModeShouldDefaultToOnChange() {
	givenAttribute(either(oneWayBindingText, twoWayBindingText));

	performInitialization();

	assertThat(currentValueCommitMode(), equalTo(ValueCommitMode.ON_CHANGE));
    }

    private ValueCommitMode currentValueCommitMode() {
	return ((TwoWayTextAttribute) childViewAttribute(TEXT)).valueCommitMode;
    }

    @Test
    public void givenTwoWayBindingTextAndValueCommitModeAttributes_thenCreateTextAttribute() {
	givenAttributes(twoWayBindingText, valueCommitMode);

	performInitialization();

	assertThatAttributeWasCreated(TwoWayTextAttribute.class);
    }

    @Test
    public void givenValueCommitModeAttribute_thenSetValueCommitModeAccordingly() {
	String valueCommitModeValue = either("onChange", "onFocusLost");
	Attribute valueCommitMode = attribute("valueCommitMode=" + valueCommitModeValue);
	givenAttributes(twoWayBindingText, valueCommitMode);

	performInitialization();

	assertThat(currentValueCommitMode(), equalTo(ValueCommitMode.from(valueCommitModeValue)));
    }

    @Test(expected = MissingRequiredAttributesException.class)
    public void givenValueCommitModeAttributeOnly_thenReject() {
	givenAttribute(valueCommitMode);

	performInitialization();
    }

    @Test(expected = MalformedAttributeException.class)
    public void givenOneWayBindingTextAndValueCommitModeAttributes_thenReject() {
	givenAttributes(oneWayBindingText, valueCommitMode);

	performInitialization();
    }

}