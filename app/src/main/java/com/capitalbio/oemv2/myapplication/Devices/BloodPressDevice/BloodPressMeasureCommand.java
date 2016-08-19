package com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice;


public class BloodPressMeasureCommand extends BloodPressCommand {

    @Override
    public void excuteCommand() {
        stateMachine.sendMessage(Constant.BLOOD_PRESS_COMMAND_MEASURE);
    }

}
