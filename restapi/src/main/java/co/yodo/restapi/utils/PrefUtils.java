package co.yodo.restapi.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.orhanobut.hawk.Hawk;

import java.util.UUID;

/**
 * Created by hei on 20/04/17.
 * Handle the utils for the preferences
 */
public class PrefUtils {
    /** The device identifier */
    private static String identifier = null;

    /** Key for the identifier */
    private static final String PREF_IDENTIFIER = "pref_identifier";

    /**
     * Generates the mobile hardware identifier either
     * from the Phone (IMEI) or the Bluetooth (MAC)
     * @param context The Context of the Android system.
     */
    @SuppressLint("HardwareIds")
    private static String generateIdentifier(Context context) {
        String hardwareToken = null;

        // Get the identifier providers
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Try to get the imei
        if (telephonyManager != null) {
            String imei = telephonyManager.getDeviceId();
            if (imei != null) {
                hardwareToken = imei.replace("/", "");
            }
        }

        // Try to get bluetooth address
        if (hardwareToken == null && bluetoothAdapter != null) {
            bluetoothAdapter.enable();
            hardwareToken = bluetoothAdapter.getAddress().replaceAll(":", "");
        }

        // Use uuid if everything else failed (02:00:00:00:00:00 is an error)
        if (hardwareToken == null || hardwareToken.equals("020000000000")) {
            hardwareToken = UUID.randomUUID().toString().replace("-", "");
        }

        return hardwareToken;
    }

    /**
     * Gets the identifier for the device
     * @return A string with the identifier
     */
    public synchronized static String getIdentifier(Context context) {
        if (identifier == null) {
            identifier = Hawk.get(PREF_IDENTIFIER);

            if (identifier == null) {
                identifier = generateIdentifier(context);
                Hawk.put(PREF_IDENTIFIER, identifier);
            }
        }

        return identifier;
    }
}
