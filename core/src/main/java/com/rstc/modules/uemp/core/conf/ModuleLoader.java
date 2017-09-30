package com.rstc.modules.uemp.core.conf;

import java.io.FileReader;

import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;

import com.rstc.modules.uemp.core.util.Core;

@Component
public class ModuleLoader {

	@Autowired
	@Qualifier("castorMarshaller")
	Unmarshaller unmarshaller;
	
	public Feature load()throws Exception{
		FileReader fr = new FileReader(Core.MODULE_XML_FILE);
		return (Feature)unmarshaller.unmarshal(new StreamSource(fr));
	}

}
