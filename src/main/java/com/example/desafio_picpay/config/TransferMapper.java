package com.example.desafio_picpay.config;

import com.example.desafio_picpay.dto.TransferDto;
import com.example.desafio_picpay.entities.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransferMapper {
    TransferMapper INSTANCE = Mappers.getMapper(TransferMapper.class);

    TransferDto transferToTransferDto(Transfer transfer);

    Transfer transferDtoToTransfer(TransferDto transferDto);
}