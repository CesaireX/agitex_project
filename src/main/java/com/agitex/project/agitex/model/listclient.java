package com.agitex.project.agitex.model;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "clients")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class listclient {
    @XmlElement(name = "client")
    private List<client> clients;
}
