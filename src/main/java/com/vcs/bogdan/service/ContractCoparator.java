package com.vcs.bogdan.service;

import com.vcs.bogdan.beans.Contract;

import java.util.Comparator;

public class ContractCoparator implements Comparator<Contract> {
    @Override
    public int compare(Contract c1, Contract c2) {
        return c1.getDate() >= c2.getDate() ? 1 : -1;
    }
}
