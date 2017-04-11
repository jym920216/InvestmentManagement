package com.winsigns.investment.inventoryService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Service;

import com.winsigns.investment.inventoryService.command.ApplyInventoryCommand;


@Service
public class InventoryService {

  @Autowired
  private StringRedisTemplate redisTemplate;

  public void apply(ApplyInventoryCommand applyInventoryCommand) {

    GenericToStringSerializer<ApplyInventoryCommand> serializer =
        new GenericToStringSerializer<ApplyInventoryCommand>(ApplyInventoryCommand.class);

    byte[] apply = serializer.serialize(applyInventoryCommand);
    // applyInventoryCommand = serializer.deserialize(apply);
    redisTemplate.boundListOps("test");
  }

}
