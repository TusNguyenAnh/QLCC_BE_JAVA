package com.mbs.qlcc.usecases.output.Complex;

import com.mbs.qlcc.entities.Complex.Complex;
import com.mbs.qlcc.usecases.request.Complex.FilterComplexInpRequest;
import com.mbs.qlcc.usecases.response.PageResponse;

import java.util.List;
import java.util.Optional;


public interface IComplexDsGateway {
    boolean existsByComplexName(String complexName);
    boolean existsByAddress(String address);
    boolean existsByPhoneContact(String phoneContact);
    Complex save(Complex complex);
    Optional<Complex> findById(String id);
    Optional<Complex> findByComplexName(String complexName);
    Optional<Complex> findByAddress(String address);
    Optional<Complex> findByPhoneContact(String phoneContact);
    PageResponse<Complex> findByStatus(int status, FilterComplexInpRequest filterComplexInpRequest);
    Complex updateStatusToApproved(String id);
    void updateStatusToRejected(String id);
    List<Complex> updateMultipleStatusToApproved(List<String> ids);
    void updateMultipleStatusToRejected(List<String> ids);
}

