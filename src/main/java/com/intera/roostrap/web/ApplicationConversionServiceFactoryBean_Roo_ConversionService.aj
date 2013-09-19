// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.intera.roostrap.web;

import com.intera.roostrap.domain.Attachment;
import com.intera.roostrap.web.ApplicationConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    public Converter<Attachment, String> ApplicationConversionServiceFactoryBean.getAttachmentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.intera.roostrap.domain.Attachment, java.lang.String>() {
            public String convert(Attachment attachment) {
                return new StringBuilder().append(attachment.getAttachmentID()).append(' ').append(attachment.getAcreatedOn()).append(' ').append(attachment.getAdesc()).append(' ').append(attachment.getAfileName()).toString();
            }
        };
    }
    
    public Converter<String, Attachment> ApplicationConversionServiceFactoryBean.getIdToAttachmentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.intera.roostrap.domain.Attachment>() {
            public com.intera.roostrap.domain.Attachment convert(java.lang.String id) {
                return Attachment.findAttachment(id);
            }
        };
    }
    
}