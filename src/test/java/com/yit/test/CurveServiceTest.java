package com.yit.test;

import com.google.common.collect.Lists;
import com.joker.model.pricecurve.PriceConfigDTO;
import com.joker.model.pricecurve.PriceSkuRealTimePriceDTO;
import com.joker.price.PriceGenerateBizServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.yit.test.extend.MyHasPropertiesMatcher.hasPropertyExt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by xiangrui on 2017/10/10.
 *
 * @author xiangrui
 * @date 2017/10/10
 */
public class CurveServiceTest {
    // region 配置
    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(CurveServiceTest.class);
    // endregion

    // region 依赖
    //@Resource
    private PriceGenerateBizServiceImpl priceGenerateBizService = new PriceGenerateBizServiceImpl();
    // endregion 

    // region 公有方法
    @Test
    public void test_generateRealTimePriceConfig_TC1() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(3);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-03 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-06 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(2);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-02 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-05 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));

        PriceConfigDTO entity03 = new PriceConfigDTO();
        entity03.setSkuId(1);
        entity03.setWeight(2);
        entity03.setPrice(new BigDecimal(115));
        entity03.setStartTime(df.parse("2017-10-01 00:00:00"));
        entity03.setEndTime(df.parse("2017-10-07 00:00:00"));
        entity03.setCreateTime(df.parse("2017-09-11 00:00:00"));

        entities.add(entity01);
        entities.add(entity02);
        entities.add(entity03);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-01 00:00:00"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-02 00:00:00")))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-02 00:00:00"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-03 00:00:00")))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-03 00:00:00"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-06 00:00:01")))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-06 00:00:01"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-07 00:00:01")))
                )
        ));

    }

    // region 公有方法
    @Test
    public void test_generateRealTimePriceConfig_TCxiangR() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(2);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-03 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-06 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(2);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-04 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-05 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));


        entities.add(entity01);
        entities.add(entity02);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-03 00:00:00"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-04 00:00:00")))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-04 00:00:00"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-05 00:00:01")))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-05 00:00:01"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-06 00:00:01")))
                )
        ));
    }


    // region 公有方法
    @Test
    public void test_generateRealTimePriceConfig_TCxiangR2() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(2);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-03 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-06 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(2);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-09 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-12 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));

        entities.add(entity01);
        entities.add(entity02);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-03 00:00:00"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-06 00:00:01")))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-09 00:00:00"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-12 00:00:01")))
                )
        ));

    }


    @Test
    public void test_generateRealTimePriceConfig_TC2() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(1);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-03 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-07 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(2);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-04 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-10 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));

        PriceConfigDTO entity03 = new PriceConfigDTO();
        entity03.setSkuId(1);
        entity03.setWeight(3);
        entity03.setPrice(new BigDecimal(115));
        entity03.setStartTime(df.parse("2017-10-02 00:00:00"));
        entity03.setEndTime(df.parse("2017-10-07 00:00:00"));
        entity03.setCreateTime(df.parse("2017-09-11 00:00:00"));

        PriceConfigDTO entity04 = new PriceConfigDTO();
        entity04.setSkuId(1);
        entity04.setWeight(4);
        entity04.setPrice(new BigDecimal(11325));
        entity04.setStartTime(df.parse("2017-10-02 00:00:00"));
        entity04.setEndTime(df.parse("2017-10-10 00:00:00"));
        entity04.setCreateTime(df.parse("2017-09-11 00:00:00"));

        entities.add(entity01);
        entities.add(entity02);
        entities.add(entity03);
        entities.add(entity04);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-02 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-10 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity04.getPrice()))
                )
        ));

    }

    @Test
    public void test_generateRealTimePriceConfig_TC3() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(1);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-03 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-07 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(2);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-01 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-04 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));

        PriceConfigDTO entity03 = new PriceConfigDTO();
        entity03.setSkuId(1);
        entity03.setWeight(3);
        entity03.setPrice(new BigDecimal(115));
        entity03.setStartTime(df.parse("2017-10-05 00:00:00"));
        entity03.setEndTime(df.parse("2017-10-06 00:00:00"));
        entity03.setCreateTime(df.parse("2017-09-11 00:00:00"));

        PriceConfigDTO entity04 = new PriceConfigDTO();
        entity04.setSkuId(1);
        entity04.setWeight(4);
        entity04.setPrice(new BigDecimal(11325));
        entity04.setStartTime(df.parse("2017-10-06 01:00:00"));
        entity04.setEndTime(df.parse("2017-10-09 00:00:00"));
        entity04.setCreateTime(df.parse("2017-09-11 00:00:00"));

        PriceConfigDTO entity05 = new PriceConfigDTO();
        entity05.setSkuId(1);
        entity05.setWeight(4);
        entity05.setPrice(new BigDecimal(11325));
        entity05.setStartTime(df.parse("2017-10-02 00:00:00"));
        entity05.setEndTime(df.parse("2017-10-08 00:00:00"));
        entity05.setCreateTime(df.parse("2017-09-11 00:00:00"));

        entities.add(entity01);
        entities.add(entity02);
        entities.add(entity03);
        entities.add(entity04);
        entities.add(entity05);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-01 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-02 00:00:00")))
                        , hasPropertyExt("price", Matchers.equalTo(entity02.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-02 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-08 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity05.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-08 00:00:01")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-09 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity04.getPrice()))
                )
        ));

    }

    @Test
    public void test_generateRealTimePriceConfig_TC4() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(1);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-03 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-07 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(2);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-01 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-04 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));

        PriceConfigDTO entity03 = new PriceConfigDTO();
        entity03.setSkuId(1);
        entity03.setWeight(3);
        entity03.setPrice(new BigDecimal(115));
        entity03.setStartTime(df.parse("2017-10-05 00:00:00"));
        entity03.setEndTime(df.parse("2017-10-06 00:00:00"));
        entity03.setCreateTime(df.parse("2017-09-11 00:00:00"));

        PriceConfigDTO entity04 = new PriceConfigDTO();
        entity04.setSkuId(1);
        entity04.setWeight(4);
        entity04.setPrice(new BigDecimal(11325));
        entity04.setStartTime(df.parse("2017-10-06 01:00:00"));
        entity04.setEndTime(df.parse("2017-10-09 00:00:00"));
        entity04.setCreateTime(df.parse("2017-09-11 00:00:00"));


        entities.add(entity01);
        entities.add(entity02);
        entities.add(entity03);
        entities.add(entity04);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-01 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-04 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity02.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-04 00:00:01")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-05 00:00:00")))
                        , hasPropertyExt("price", Matchers.equalTo(entity01.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-05 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-06 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity03.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-06 00:00:01")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-06 01:00:00")))
                        , hasPropertyExt("price", Matchers.equalTo(entity01.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-06 01:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-09 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity04.getPrice()))
                )
        ));

    }

    @Test
    public void test_generateRealTimePriceConfig_TC5() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(1);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-02 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-05 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(2);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-05 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-08 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));

        PriceConfigDTO entity03 = new PriceConfigDTO();
        entity03.setSkuId(1);
        entity03.setWeight(3);
        entity03.setPrice(new BigDecimal(115));
        entity03.setStartTime(df.parse("2017-10-08 00:00:00"));
        entity03.setEndTime(df.parse("2017-10-11 00:00:00"));
        entity03.setCreateTime(df.parse("2017-09-11 00:00:00"));

        PriceConfigDTO entity04 = new PriceConfigDTO();
        entity04.setSkuId(1);
        entity04.setWeight(4);
        entity04.setPrice(new BigDecimal(11325));
        entity04.setStartTime(df.parse("2017-10-01 00:00:00"));
        entity04.setEndTime(df.parse("2017-10-03 00:00:00"));
        entity04.setCreateTime(df.parse("2017-09-11 00:00:00"));

        PriceConfigDTO entity05 = new PriceConfigDTO();
        entity05.setSkuId(1);
        entity05.setWeight(5);
        entity05.setPrice(new BigDecimal(2325));
        entity05.setStartTime(df.parse("2017-10-04 00:00:00"));
        entity05.setEndTime(df.parse("2017-10-06 00:00:00"));
        entity05.setCreateTime(df.parse("2017-09-11 00:00:00"));

        PriceConfigDTO entity06 = new PriceConfigDTO();
        entity06.setSkuId(1);
        entity06.setWeight(6);
        entity06.setPrice(new BigDecimal(11326));
        entity06.setStartTime(df.parse("2017-10-07 00:00:00"));
        entity06.setEndTime(df.parse("2017-10-09 00:00:00"));
        entity06.setCreateTime(df.parse("2017-09-11 00:00:00"));

        PriceConfigDTO entity07 = new PriceConfigDTO();
        entity07.setSkuId(1);
        entity07.setWeight(7);
        entity07.setPrice(new BigDecimal(11327));
        entity07.setStartTime(df.parse("2017-10-10 00:00:00"));
        entity07.setEndTime(df.parse("2017-10-12 00:00:00"));
        entity07.setCreateTime(df.parse("2017-09-11 00:00:00"));


        entities.add(entity01);
        entities.add(entity02);
        entities.add(entity03);
        entities.add(entity04);
        entities.add(entity05);
        entities.add(entity06);
        entities.add(entity07);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-01 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-03 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity04.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-03 00:00:01")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-04 00:00:00")))
                        , hasPropertyExt("price", Matchers.equalTo(entity01.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-04 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-06 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity05.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-06 00:00:01")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-07 00:00:00")))
                        , hasPropertyExt("price", Matchers.equalTo(entity02.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-07 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-09 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity06.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-09 00:00:01")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-10 00:00:00")))
                        , hasPropertyExt("price", Matchers.equalTo(entity03.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-10 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-12 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity07.getPrice()))
                )
        ));

    }

    @Test
    public void test_generateRealTimePriceConfig_TC6() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(1);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-02 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-05 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(2);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-05 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-08 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));

        PriceConfigDTO entity03 = new PriceConfigDTO();
        entity03.setSkuId(1);
        entity03.setWeight(3);
        entity03.setPrice(new BigDecimal(115));
        entity03.setStartTime(df.parse("2017-10-08 00:00:00"));
        entity03.setEndTime(df.parse("2017-10-11 00:00:00"));
        entity03.setCreateTime(df.parse("2017-09-11 00:00:00"));

        entities.add(entity01);
        entities.add(entity02);
        entities.add(entity03);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-02 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-05 00:00:00")))
                        , hasPropertyExt("price", Matchers.equalTo(entity01.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-05 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-08 00:00:00")))
                        , hasPropertyExt("price", Matchers.equalTo(entity02.getPrice()))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-08 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-11 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity03.getPrice()))
                )
        ));

    }

    @Test
    public void test_generateRealTimePriceConfig_TC7() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(1);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-02 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-02 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(2);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-02 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-02 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));

        PriceConfigDTO entity03 = new PriceConfigDTO();
        entity03.setSkuId(1);
        entity03.setWeight(3);
        entity03.setPrice(new BigDecimal(115));
        entity03.setStartTime(df.parse("2017-10-02 00:00:00"));
        entity03.setEndTime(df.parse("2017-10-02 00:00:00"));
        entity03.setCreateTime(df.parse("2017-09-11 00:00:00"));

        entities.add(entity01);
        entities.add(entity02);
        entities.add(entity03);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-02 00:00:00")))
                        , hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-02 00:00:01")))
                        , hasPropertyExt("price", Matchers.equalTo(entity03.getPrice()))
                )
        ));

    }

    @Test
    public void test_ge_tc8() throws ParseException {
        List<PriceConfigDTO> entities = Lists.newArrayList();
        PriceConfigDTO entity01 = new PriceConfigDTO();
        entity01.setSkuId(1);
        entity01.setWeight(3);
        entity01.setPrice(new BigDecimal(120));
        entity01.setStartTime(df.parse("2017-10-03 00:00:00"));
        entity01.setEndTime(df.parse("2017-10-07 00:00:00"));
        entity01.setCreateTime(df.parse("2017-09-01 00:00:00"));

        PriceConfigDTO entity02 = new PriceConfigDTO();
        entity02.setSkuId(1);
        entity02.setWeight(5);
        entity02.setPrice(new BigDecimal(110));
        entity02.setStartTime(df.parse("2017-10-05 00:00:00"));
        entity02.setEndTime(df.parse("2017-10-07 00:00:00"));
        entity02.setCreateTime(df.parse("2017-09-18 00:00:00"));

        entities.add(entity01);
        entities.add(entity02);

        List<PriceSkuRealTimePriceDTO> list = priceGenerateBizService.generateRealTimePriceConfig(entities);

        assertThat(list, contains(
                allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-03 00:00:00"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-05 00:00:00")))
                )
                , allOf(
                        hasPropertyExt("startTime", Matchers.equalTo(df.parse("2017-10-05 00:00:00"))),
                        hasPropertyExt("endTime", Matchers.equalTo(df.parse("2017-10-07 00:00:01")))
                )
        ));
    }

    // endregion 

    // region 私有方法
    // endregion     
}
