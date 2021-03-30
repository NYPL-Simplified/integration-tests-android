package util.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "feed")
public class FeedModel {
    private List<Entry> entries;
    private List<LinkFromFeed> linksFromFeed;

    public List<Entry> getEntries() {
        return entries;
    }

    @XmlElement(name = "entry")
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<LinkFromFeed> getLinksFromFeed() {
        return linksFromFeed;
    }

    @XmlElement(name = "link")
    public void setLinksFromFeed(List<LinkFromFeed> linksFromFeed) {
        this.linksFromFeed = linksFromFeed;
    }
}

class Entry {
    private String bookName;
    private String bookType;
    private Distribution distributor;
    private List<LinkFromEntry> linksFromEntry;

    public String getBookName() {
        return bookName;
    }

    @XmlElement(name = "title")
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookType() {
        return bookType;
    }

    @XmlAttribute(name = "additionalType", namespace = "http://schema.org/")
    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public Distribution getDistributor() {
        return distributor;
    }

    @XmlElement(name = "distribution")
    public void setDistributor(Distribution distributor) {
        this.distributor = distributor;
    }

    public List<LinkFromEntry> getLinksFromEntry() {
        return linksFromEntry;
    }

    @XmlElement(name = "link")
    public void setLinksFromEntry(List<LinkFromEntry> linksFromEntry) {
        this.linksFromEntry = linksFromEntry;
    }
}

class Distribution {
    private String distributorName;

    public String getDistributorName() {
        return distributorName;
    }

    @XmlAttribute(name = "ProviderName", namespace = "http://bibframe.org/vocab/")
    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }
}

class LinkFromEntry {
    private Copies copies;

    public Copies getCopies() {
        return copies;
    }

    @XmlElement(name = "copies")
    public void setCopies(Copies copies) {
        this.copies = copies;
    }
}

class LinkFromFeed {
    private String nextURLForXML;
    private String conditionForNextXML;

    public String getNextURLForXML() {
        return nextURLForXML;
    }

    @XmlAttribute(name = "href")
    public void setNextURLForXML(String nextURLForXML) {
        this.nextURLForXML = nextURLForXML;
    }

    public String getConditionForNextXML() {
        return conditionForNextXML;
    }

    @XmlAttribute(name = "rel")
    public void setConditionForNextXML(String conditionForNextXML) {
        this.conditionForNextXML = conditionForNextXML;
    }
}

class Copies {
    private int countAvailableCopies;

    public int getCountAvailableCopies() {
        return countAvailableCopies;
    }

    @XmlAttribute(name = "available")
    public void setCountAvailableCopies(int countAvailableCopies) {
        this.countAvailableCopies = countAvailableCopies;
    }
}
