package by.itechart.flowerty.web.controller;

 
import by.itechart.flowerty.persistence.model.OrderAltering;
import by.itechart.flowerty.web.model.OrderCreateBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import by.itechart.flowerty.persistence.model.Order;
import by.itechart.flowerty.web.model.OrderEditBundle;
import by.itechart.flowerty.web.service.OrderService;

/**
 * Created by Катерина on 24.04.2015.
 *
 * Controller for processing all the requests connected with orders
 */

@Controller
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "order/save", method = RequestMethod.POST)
    public Order save(@RequestBody Order orderToSave){
        LOGGER.info("Saving order: {}", orderToSave);
        Order savedOrder = orderService.save(orderToSave);
        return savedOrder;
    }

    @RequestMapping(value = "order/change/save", method = RequestMethod.POST)
    public Order saveChanges(@RequestBody OrderEditBundle orderEditBundle){
        return orderService.saveChanges(orderEditBundle);
    }

    @ResponseBody
    @RequestMapping(value = "order/list/{page}", method = RequestMethod.GET)
    public Page<Order> page(@PathVariable("page") Integer page){
        LOGGER.info("Getting order page with number {}", page);

        page = (page == null || page < 1) ? 0 : --page;
        return orderService.getPage(page, 10);
    }

    @ResponseBody
    @RequestMapping(value = "order/details/{id}", method = RequestMethod.GET)
    public OrderEditBundle details(@PathVariable("id") Long id) throws Exception{
        LOGGER.info("get details about contact with id: {}", id);

        if (id == null || id < 0) {
            throw new Exception("contact id cannot be negative or null");
        }

        OrderEditBundle order = orderService.getOrderEditBundleById(id);
        return order;
    }

    @ResponseBody
    @RequestMapping(value = "order/create/bundle", method = RequestMethod.GET)
    public OrderCreateBundle createBundle() throws Exception{
        LOGGER.info("getting prepared order bundle for creating a new one");
        return orderService.getOrderCreateBundle();
    }
}
