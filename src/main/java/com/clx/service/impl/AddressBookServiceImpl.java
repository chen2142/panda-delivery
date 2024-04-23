package com.clx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.entity.AddressBook;
import com.clx.mapper.AddressBookMapper;
import com.clx.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
