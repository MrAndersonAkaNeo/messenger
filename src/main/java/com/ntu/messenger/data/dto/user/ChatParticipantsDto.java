package com.ntu.messenger.data.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ChatParticipantsDto {

    @NotNull
    @Size(min = 2)
    private List<Long> participantsIds;

}
