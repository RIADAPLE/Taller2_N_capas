package com.uca.spring.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NumberBoxTag extends SimpleTagSupport {

    private String id;
    private String name;
    private String tabindex;
    private String required;
    private String readonly;
    private Integer precision;
    private Integer scale;
    private String prefix;
    private String value;

    @Override
    public void doTag() throws JspException, IOException {
        StringBuilder scriptBuilder = new StringBuilder();
        Double min = 0.1;
        Integer max;
        try {
            scriptBuilder.append(" <input type=\"text\" autocomplete=\"off\" data-masked ");

            if (scale == null) {
                scale = 0;
                min = 1.0;
                
               
            }

            
            if (id != null && !id.trim().equals("")) {
                scriptBuilder.append("id=\"").append(id).append("\" ");
            }

            if (name != null && !name.trim().equals("")) {
                scriptBuilder.append(" name=\"").append(name).append("\"");
            }

            if (required != null && required.trim().equals("true")) {
                scriptBuilder.append(" required=\"").append(required).append("\" ");
            }

            if (tabindex != null && !tabindex.trim().equals("")) {
                scriptBuilder.append(" tabindex=\"").append(tabindex).append("\" ");
            }

            if (readonly != null && readonly.trim().equals("true")) {
                scriptBuilder.append(" readonly=\"true\" ");
            }
            
            if (value != null && !value.trim().equals("")) {
                scriptBuilder.append(" value=\"").append(value).append("\"");
            }

            scriptBuilder.append(" class=\"form-control\" >\n");

            scriptBuilder.append("<script>\n");
            scriptBuilder.append("$(document).ready(function(){\n");
            scriptBuilder.append("  $('#"+id+"').inputmask(\"decimal\",{\n");
            scriptBuilder.append("       min:").append(0.0).append(", \n");
            scriptBuilder.append("       max:").append(BigDecimal.valueOf(Math.pow(10,precision-1)).toPlainString()).append(", \n");
            scriptBuilder.append("       radixPoint:\".\", \n");
            scriptBuilder.append("       groupSeparator: \",\", \n");
            scriptBuilder.append("       digits: ").append(scale).append(",\n");
            scriptBuilder.append("       autoGroup: true,\n");
            if(prefix!=null && !prefix.equals("")){
                scriptBuilder.append("       prefix: ").append(prefix).append(" ,\n");
            
            }
            scriptBuilder.append("       autoUnmask: true \n");
            scriptBuilder.append("  });\n");
            scriptBuilder.append("});\n");
            scriptBuilder.append("</script>\n");
            getJspContext().getOut().write(scriptBuilder.toString());

        } catch (Exception ex) {
            getJspContext().getOut().write("error textbox");
        }
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}


