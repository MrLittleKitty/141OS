public class Disk {

    private static final int NUM_OF_SECTORS = 1024;
    private final StringBuffer[] sectors;
    private final int id;
    private final int sectorSize;

    public Disk(int size, int id) {
        this.id = id;
        this.sectors = new StringBuffer[1024];
        sectorSize = size / NUM_OF_SECTORS;
        for(int i = 0; i < sectors.length; i++) {
            sectors[i] = new StringBuffer(sectorSize);
        }
    }

    public int getId() {
        return this.id;
    }

    public void write(int sector, StringBuffer data) {
        sectors[sector].setLength(0);
        sectors[sector].append(data);
    }

    public void read(int sector, StringBuffer data) {
        data.append(sectors[sector]);
    }

    public int getSectorSize() {
        return this.sectorSize;
    }
}
