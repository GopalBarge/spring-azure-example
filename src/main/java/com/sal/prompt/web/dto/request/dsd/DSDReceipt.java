package com.sal.prompt.web.dto.request.dsd;

import com.sal.prompt.web.dto.request.SourceSystemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DSDReceipt implements SourceSystemRequest {
    String recptNbr;
    List<DSDReceiptRequest> lines;
}
