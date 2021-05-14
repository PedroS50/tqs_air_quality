package detiua.tqs.pedro93221.air_quality.cache;

import java.util.Objects;

public class CacheDetails {
    private String type;
    private int hits;
    private int misses;
    private int nRequests;

    public CacheDetails(String type) {
        this.type = type;
        this.hits = 0;
        this.misses = 0;
        this.nRequests = 0;
    }

    public CacheDetails(String type, int hits, int misses, int nRequests) {
        this.type = type;
        this.hits = hits;
        this.misses = misses;
        this.nRequests = nRequests;
    }

    public void addHit() {
        this.hits++;
    }

    public void addMiss() {
        this.misses++;
    }

    public void addRequest() {
        this.nRequests++;
    }

    public String getType() {
        return this.type;
    }

    public int getHits() {
        return this.hits;
    }

    public int getMisses() {
        return this.misses;
    }

    public int getNRequests() {
        return this.nRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CacheDetails)) {
            return false;
        }
        var cacheDetails = (CacheDetails) o;
        return Objects.equals(type, cacheDetails.type) && hits == cacheDetails.hits && misses == cacheDetails.misses
                && nRequests == cacheDetails.nRequests;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, hits, misses, nRequests);
    }

    @Override
    public String toString() {
        return "{" + " type='" + getType() + "'" + ", hits='" + getHits() + "'" + ", misses='" + getMisses() + "'"
                + ", nRequests='" + getNRequests() + "'" + "}";
    }

}
