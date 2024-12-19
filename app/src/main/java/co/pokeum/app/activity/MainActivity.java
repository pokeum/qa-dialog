package co.pokeum.app.activity;

import static co.pokeum.app.Global.*;
import static co.pokeum.inputs.dialog.model.ResultModel.getType;
import static co.pokeum.inputs.dialog.model.ResultModel.getValue;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import co.ab180.airbridge.Airbridge;
import co.ab180.airbridge.common.AirbridgeAttribute;
import co.pokeum.app.R;
import co.pokeum.app.databinding.ActivityMainBinding;
import co.pokeum.inputs.dialog.InputsDialogFragment;
import co.pokeum.inputs.dialog.InputsDialogInterface;
import co.pokeum.inputs.dialog.model.InputModel;
import co.pokeum.inputs.dialog.model.InputModelBuilder;
import co.pokeum.inputs.dialog.model.InputModelType;

public class MainActivity extends AppCompatActivity implements InputsDialogInterface.ResultListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initButtons();
    }

    private void initButtons() {
        // Set User Id
        binding.setUserIdButton.setOnClickListener(view ->
                new InputsDialogFragment.Builder()
                        .setTitle("User ID")
                        .setInputs(
                                new ArrayList<InputModel>() {{
                                    add(new InputModelBuilder(KEY_USER_ID, InputModelType.KeyValue)
                                            .hideLabel()
                                            .setValueContentDescription("userId")
                                            .setHint("Enter User ID")
                                            .build());
                                }}
                        )
                        .setListener(this)
                        .setListenerId(R.id.setUserIdButton)
                        .setPositiveButtonText("Save")
                        .setPositiveButtonContentDescription("save")
                        .build()
                        .show(getSupportFragmentManager())
        );

        // Add User Alias
        binding.addUserAliasButton.setOnClickListener(view ->
                new InputsDialogFragment.Builder()
                        .setTitle("Entry")
                        .setInputs(new ArrayList<InputModel>() {{
                            add(new InputModelBuilder(KEY_USER_ALIAS_KEY, InputModelType.KeyValue)
                                    .setLabel("Key")
                                    .setValueContentDescription("key")
                                    .build());
                            add(new InputModelBuilder(KEY_USER_ALIAS_VALUE, InputModelType.KeyValue)
                                    .setLabel("Value")
                                    .setValueContentDescription("value")
                                    .build());
                        }})
                        .setListener(this)
                        .setListenerId(R.id.addUserAliasButton)
                        .setPositiveButtonText("Save")
                        .setPositiveButtonContentDescription("save")
                        .build()
                        .show(getSupportFragmentManager())
        );

        // Add User Attribute
        binding.addUserAttributeButton.setOnClickListener(view ->
                new InputsDialogFragment.Builder()
                        .setTitle("Entry")
                        .setInputs(new ArrayList<InputModel>() {{
                            add(new InputModelBuilder(KEY_USER_ATTRIBUTE_KEY, InputModelType.KeyValue)
                                    .setLabel("Key")
                                    .setValueContentDescription("key")
                                    .build());
                            add(new InputModelBuilder(KEY_USER_ATTRIBUTE_VALUE, InputModelType.KeyValueType)
                                    .setLabel("Value")
                                    .setValueContentDescription("value")
                                    .setTypeContentDescription("type")
                                    .setTypeList(Arrays.asList(attributeTypes))
                                    .setTypePosition(5)
                                    .build());
                        }})
                        .setListener(this)
                        .setListenerId(R.id.addUserAttributeButton)
                        .setPositiveButtonText("Save")
                        .setPositiveButtonContentDescription("save")
                        .build()
                        .show(getSupportFragmentManager())
        );

        // Track Custom Event
        binding.customEventButton.setOnClickListener(view ->
                new InputsDialogFragment.Builder()
                        .setTitle("CUSTOM EVENT")
                        .setInputs(new ArrayList<InputModel>() {{
                            add(new InputModelBuilder(KEY_CUSTOM_EVENT_CATEGORY, InputModelType.KeyValue)
                                    .setLabel("Category")
                                    .setValueContentDescription("customEventCategory")
                                    .setDefaultValue("custom_category")
                                    .build());
                            add(new InputModelBuilder(KEY_CUSTOM_EVENT_ACTION, InputModelType.KeyValue)
                                    .setLabel("Action")
                                    .setValueContentDescription("customEventAction")
                                    .setDefaultValue("custom_action")
                                    .build());
                            add(new InputModelBuilder(KEY_CUSTOM_EVENT_LABEL, InputModelType.KeyValue)
                                    .setLabel("Label")
                                    .setValueContentDescription("customEventLabel")
                                    .setDefaultValue("custom_label")
                                    .build());
                            add(new InputModelBuilder(KEY_CUSTOM_EVENT_VALUE, InputModelType.KeyValue)
                                    .setLabel("Value")
                                    .setValueContentDescription("customEventValue")
                                    .setDefaultValue("1234.0")
                                    .build());
                            add(new InputModelBuilder(KEY_CUSTOM_EVENT_SEMANTIC_ATTRIBUTES, InputModelType.KeyValue)
                                    .setLabel("Semantic Attributes")
                                    .setValueContentDescription("customEventSemanticAttributes")
                                    .build());
                            add(new InputModelBuilder(KEY_CUSTOM_EVENT_CUSTOM_ATTRIBUTES, InputModelType.KeyValue)
                                    .setLabel("Custom Attributes")
                                    .setValueContentDescription("customEventCustomAttributes")
                                    .build());
                        }})
                        .setListener(this)
                        .setListenerId(R.id.customEventButton)
                        .build()
                        .show(getSupportFragmentManager())
        );

        // Show Web View
        binding.showWebViewButton.setOnClickListener(view -> startActivity(WebViewActivity.newIntent(this)));
    }

    @Override
    @SuppressLint("NonConstantResourceId")
    public void onPositiveButtonClick(DialogInterface dialog, int id, @NonNull String result) {
        try {
            switch (id) {
                case R.id.setUserIdButton:
                    Airbridge.setUserID(getValue(result, KEY_USER_ID));
                    dialog.dismiss();
                    break;
                case R.id.addUserAliasButton:
                    Airbridge.setUserAlias(getValue(result, KEY_USER_ALIAS_KEY), getValue(result, KEY_USER_ALIAS_VALUE));
                    dialog.dismiss();
                    break;
                case R.id.addUserAttributeButton:
                    String type = attributeTypes[getType(result, KEY_USER_ATTRIBUTE_VALUE)];
                    switch (type) {
                        case "Int":
                            try {
                                Airbridge.setUserAttribute(getValue(result, KEY_USER_ATTRIBUTE_KEY), Integer.parseInt(getValue(result, KEY_USER_ATTRIBUTE_VALUE)));
                                dialog.dismiss();
                            } catch (NumberFormatException e) {
                                Toast.makeText(this, "Invalid integer: " + getValue(result, KEY_USER_ATTRIBUTE_VALUE),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "Long":
                            try {
                                Airbridge.setUserAttribute(getValue(result, KEY_USER_ATTRIBUTE_KEY), Long.parseLong(getValue(result, KEY_USER_ATTRIBUTE_VALUE)));
                                dialog.dismiss();
                            } catch (NumberFormatException e) {
                                Toast.makeText(this, "Invalid long: " + getValue(result, KEY_USER_ATTRIBUTE_VALUE),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "Float":
                            try {
                                Airbridge.setUserAttribute(getValue(result, KEY_USER_ATTRIBUTE_KEY), Float.parseFloat(getValue(result, KEY_USER_ATTRIBUTE_VALUE)));
                                dialog.dismiss();
                            } catch (NumberFormatException e) {
                                Toast.makeText(this, "Invalid float: " + getValue(result, KEY_USER_ATTRIBUTE_VALUE),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "Double":
                            try {
                                Airbridge.setUserAttribute(getValue(result, KEY_USER_ATTRIBUTE_KEY), Double.parseDouble(getValue(result, KEY_USER_ATTRIBUTE_VALUE)));
                                dialog.dismiss();
                            } catch (NumberFormatException e) {
                                Toast.makeText(this, "Invalid double: " + getValue(result, KEY_USER_ATTRIBUTE_VALUE),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "Boolean":
                            try {
                                Airbridge.setUserAttribute(getValue(result, KEY_USER_ATTRIBUTE_KEY), Boolean.parseBoolean(getValue(result, KEY_USER_ATTRIBUTE_VALUE)));
                                dialog.dismiss();
                            } catch (NumberFormatException e) {
                                Toast.makeText(this, "Invalid boolean: " + getValue(result, KEY_USER_ATTRIBUTE_VALUE),
                                        Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "String":
                            Airbridge.setUserAttribute(getValue(result, KEY_USER_ATTRIBUTE_KEY), getValue(result, KEY_USER_ATTRIBUTE_VALUE));
                            dialog.dismiss();
                            break;
                    }
                    break;
                case R.id.customEventButton:
                    Airbridge.trackEvent(getValue(result, KEY_CUSTOM_EVENT_CATEGORY),
                            new HashMap<String, Object>() {{
                                put(AirbridgeAttribute.ACTION, getValue(result, KEY_CUSTOM_EVENT_ACTION));
                                put(AirbridgeAttribute.LABEL, getValue(result, KEY_CUSTOM_EVENT_LABEL));
                                put(AirbridgeAttribute.VALUE, Float.parseFloat(getValue(result, KEY_CUSTOM_EVENT_VALUE)));
                            }}
                    );
                    dialog.dismiss();
                    break;
            }
        } catch (Throwable ignore) { /* NoSuchElementException, NumberFormatException */ }
    }
}