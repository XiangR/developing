package com.joker.price;

import com.google.common.collect.Lists;
import com.joker.model.ServiceRuntimeException;
import com.joker.model.pricecurve.AbstractPriceEntity;
import com.joker.model.pricecurve.CurvePriceNode;
import com.joker.model.pricecurve.PriceConfigDTO;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by xiangrui on 2017/10/9.
 *
 * @author xiangrui
 * @date 2017/10/09
 */
public class CurveService {
    // region 配置
    /**
     * 日志工具
     */
//    private static final Logger LOGGER = LoggerFactory.getLogger(CurveService.class);

    public static final int MILLION_PER_SECOND = 1000;

    List<CurvePriceNode> priceNodes = Lists.newArrayList();

    // endregion 

    // region 依赖
    // endregion 

    // region 公有方法

    /**
     * 生成价格曲线，不并掉价格相同的节点
     *
     * @param collect
     */
    public void generateRealTimePrice(List<PriceConfigDTO> collect) {
        for (PriceConfigDTO priceInfo : collect) {
            try {
                insert(new CurvePriceNode(priceInfo.getPrice(), priceInfo.getStartTime(), priceInfo),
                        new CurvePriceNode(new BigDecimal(0), new Date(priceInfo.getEndTime().getTime() + MILLION_PER_SECOND), null));
            } catch (Exception e) {
                throw new RuntimeException("生成实时价格失败");
            }
        }
    }

    // endregion 

    // region 私有方法

    private void insert(CurvePriceNode startNode, CurvePriceNode endNode)
            throws InstantiationException, IllegalAccessException {
        // 插入节点
        int startNodeIndex = insertStartNode(startNode);
        int endNodeIndex = insertEndNode(endNode);

        for (int index = endNodeIndex - 1; index >= 0; --index) {
            if (index == startNodeIndex) {
                continue;
            }
            CurvePriceNode previousCurvePriceNode = priceNodes.get(index);
            endNode.setPrice(previousCurvePriceNode.getPrice());
            AbstractPriceEntity config = copyFrom(previousCurvePriceNode.getConfig());
            if (Objects.nonNull(config)) {
                config.setStartTime(endNode.getEffectiveTime());
            }
            endNode.setConfig(config);
            break;
        }

        // 删除中间的节点
        for (int index = endNodeIndex - 1; index > startNodeIndex; --index) {
            priceNodes.remove(index);
        }
    }

    private int compare(CurvePriceNode node1, CurvePriceNode node2) {
        return node1.getEffectiveTime().compareTo(node2.getEffectiveTime());
    }

    private int insertStartNode(CurvePriceNode node) {
        for (int index = priceNodes.size() - 1; index >= 0; --index) {
            if (compare(priceNodes.get(index), node) < 0) {
                priceNodes.add(index + 1, node);
                return index + 1;
            }
        }
        priceNodes.add(0, node);
        return 0;
    }

    private int insertEndNode(CurvePriceNode node) {
        for (int index = priceNodes.size() - 1; index >= 0; --index) {
            if (compare(priceNodes.get(index), node) <= 0) {
                priceNodes.add(index + 1, node);
                return index + 1;

            }
        }
        priceNodes.add(0, node);
        return 0;
    }

    private AbstractPriceEntity copyFrom(AbstractPriceEntity source)
            throws IllegalAccessException, InstantiationException {
        if (Objects.isNull(source)) {
            return null;
        }
        Class sourceClazz = source.getClass();
        AbstractPriceEntity result = (AbstractPriceEntity) sourceClazz.newInstance();
        BeanUtils.copyProperties(source, result);
        return result;
    }
    // endregion

    //public static void main(String[] args) throws InstantiationException, IllegalAccessException {
    //    PriceConfigDTO dto = new PriceConfigDTO();
    //    dto.setId(1);
    //    dto.setPrice(new BigDecimal("12"));
    //    dto.setUpdateTime(new Date());
    //    AbstractPriceEntity abstractPriceEntity = new CurveService().copyFrom(dto);
    //    LOGGER.info("{}", JSON.toJSONString(abstractPriceEntity));
    //}
}
