package com.yit.test;

import com.joker.spi.simple.SpiServiceLoader;
import com.joker.spi.simple.SpiService;
import com.joker.spi.simple.SpiServiceAImpl;
import com.joker.spi.simple.SpiServiceBImpl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created by xiangrui on 2019-06-25.
 *
 * @author xiangrui
 * @date 2019-06-25
 */
public class SpiServiceLoaderTest {

    @Test
    public void test_getSpiLoader_WithSupport() {
        {
            SpiService spiService = SpiServiceLoader.getExtensionBySupport("A");
            assertThat(spiService, instanceOf(SpiServiceAImpl.class));
        }
        {
            SpiService spiService = SpiServiceLoader.getExtensionBySupport("B");
            assertThat(spiService, instanceOf(SpiServiceBImpl.class));
        }
    }

    @Test
    public void test_getSpiLoader() {
        {
            SpiService spiService = SpiServiceLoader.getExtension("SpiServiceAImpl");
            assertThat(spiService, instanceOf(SpiServiceAImpl.class));
        }
        {
            SpiService spiService = SpiServiceLoader.getExtension("SpiServiceBImpl");
            assertThat(spiService, instanceOf(SpiServiceBImpl.class));
        }
    }


    @Test
    public void test_getSpiLoader_DefaultExtension() {

        SpiService defaultExtension = SpiServiceLoader.getDefaultExtension();
        assertThat(defaultExtension, instanceOf(SpiServiceAImpl.class));
    }

}
