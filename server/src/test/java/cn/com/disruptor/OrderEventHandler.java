package cn.com.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderEventHandler implements EventHandler<OrderEvent>, WorkHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) {
        System.out.println("OrderEvent "+event.getId());
    }

    @Override
    public void onEvent(OrderEvent event) {
        System.out.println("OrderEvent "+event.getId());
    }

}
