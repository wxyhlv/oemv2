package com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice;

public interface BloodPressCommandListener {
    public void onCommandResult(CommandResult commandResult);
    public void onGattDisconnect();
    public void onDiscoverServiceComeplete();
}
