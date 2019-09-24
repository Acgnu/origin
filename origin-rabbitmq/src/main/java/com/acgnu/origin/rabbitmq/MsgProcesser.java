package com.acgnu.origin.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public class MsgProcesser {
    /**
     * rabbitmq消息监听器
     * 如果此函数异常，消息将会重复推送
     * @param content
     */
//    @RabbitListener(queues = RabbitmqConfig.QUEEN_NAME)
    public void processMessage(String content) {
//        try {
            System.out.println("mq msg received at 01:" + content);
//        } catch (Exception e) {
            //出现异常抛出此异常，拒收此消息。
//            throw new AmqpRejectAndDontRequeueException(e.getMessage());
//        }

    }

//    @RabbitListener(queues = RabbitmqConfig.SYNC_QUEEN_NAME)
//    public String processMessage2(String content) {
//        return content;
//    }
}
