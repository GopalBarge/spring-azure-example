package com.sal.prompt.web.dto.request.supplychain;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class SupplychainReceipt implements SourceSystemRequest {
    String recptNbr;
    List<SupplyChainReceiptRequest> lines;
}
