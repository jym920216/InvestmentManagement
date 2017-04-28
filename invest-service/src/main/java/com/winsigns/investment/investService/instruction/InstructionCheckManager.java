package com.winsigns.investment.investService.instruction;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.winsigns.investment.framework.spring.SpringManager;
import com.winsigns.investment.investService.constant.InstructionMessageType;
import com.winsigns.investment.investService.model.Instruction;
import com.winsigns.investment.investService.model.InstructionMessage;
import com.winsigns.investment.investService.repository.InstructionMessageRepository;

/**
 * 指令检查的管理者
 * 
 * @author yimingjin
 *
 */
@Component
public class InstructionCheckManager {

  @Autowired
  InstructionMessageRepository instructionMessageRepository;

  static public InstructionCheckManager getInstructionCheckManager() {
    return SpringManager.getApplicationContext().getBean(InstructionCheckManager.class);
  }

  private List<IInstructionCheck> checks = new ArrayList<IInstructionCheck>();

  public void register(IInstructionCheck check) {
    checks.add(check);
  }

  public List<InstructionMessage> check(Instruction thisInstruction) {
    List<InstructionMessage> messages = new ArrayList<InstructionMessage>();
    for (IInstructionCheck check : checks) {
      IInstructionCheckResult result = check.check(thisInstruction);
      if (result != null) {
        messages.add(new InstructionMessage(thisInstruction, check.getField(),
            result.getMessageType(), result.getMessageCode()));
      }
    }
    return messages;
  }

  @Transactional
  public void checkAndUpdate(Instruction thisInstruction) {
    instructionMessageRepository.delete(thisInstruction.getMessages());
    thisInstruction.getMessages().clear();
    List<InstructionMessage> messages = check(thisInstruction);
    thisInstruction.setMessages(messages);
    instructionMessageRepository.save(messages);
  }

  @Transactional
  public boolean commitCheck(Instruction thisInstruction) {
    checkAndUpdate(thisInstruction);
    for (InstructionMessage message : thisInstruction.getMessages()) {
      if (message.getMessageType().equals(InstructionMessageType.ERROR)) {
        return false;
      }
    }
    return true;
  }

}
