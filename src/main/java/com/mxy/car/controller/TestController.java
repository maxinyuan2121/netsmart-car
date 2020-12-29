package com.mxy.car.controller;

import com.pi4j.io.gpio.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
    public class TestController {

        // create gpio controller instance
        final GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput myLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,   // PIN NUMBER
                "My LED",           // PIN FRIENDLY NAME (optional)
                PinState.LOW);

        @RequestMapping("/open")
        @ResponseBody
        public String open(){
            // PIN STARTUP STATE (optional)
            myLed.setState(PinState.HIGH);
            return "open";
        }

        @RequestMapping("/close")
        @ResponseBody
        public String close(){
            myLed.setState(PinState.LOW);
            return "close";
        }

    }

