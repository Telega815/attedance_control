package ru.icerebro.attedance_control.services.interfaces;

public interface HtmlGenerator {
    void setElementType(String tag);
    void addAttribute(String attrName, String attrValue);
    void setInnerText(String innerText);
    void setFullTag(boolean fullTag);
}
