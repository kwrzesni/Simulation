package maps_elements.data_types;

import java.util.Collection;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Genome {
    private static final int length = 32;
    private static final int nGeneTypes = 8;
    private static final Random random = new Random();
    private final int[] genes;

    public Genome() {
        this.genes = new int[length];

        for(int gene : genes) {
            gene = random.nextInt(nGeneTypes);
        }

        correctGenes();
    }

    private Genome(int[] genes) {
        this.genes = genes;
    }

    public int[] getGenes() {
        return genes;
    }

    public int getRandomGene() {
        return genes[random.nextInt(length)];
    }

    private int getFirstNotIncludedGene() {
        for(int i=0; i<nGeneTypes; ++i) {
            boolean containIGene = false;
            for(int gene : genes) {
                if(gene == i) {
                    containIGene = true;
                    break;
                }
            }
            if(!containIGene) {
                return i;
            }
        }
        return nGeneTypes;
    }

    private void addGene(int gene) {
        int geneIndex = random.nextInt(length);
        genes[geneIndex] = gene;
    }

    private void correctGenes() {
        int firstNotIncludedGene = getFirstNotIncludedGene();
        while(firstNotIncludedGene != nGeneTypes) {
            addGene(firstNotIncludedGene);
            firstNotIncludedGene = getFirstNotIncludedGene();
        }
        Arrays.sort(genes);
    }

    public static Genome crrosGenes(Genome genome1, Genome genome2) {
        int[] shuffeledGenes1 = getShuffledArray(genome1.getGenes());
        int[] shuffeledGenes2 = getShuffledArray(genome2.getGenes());

        int splitIndex = Math.max(random.nextInt(length), random.nextInt(length));
        int[] crossedGenes = Arrays.copyOf(shuffeledGenes1, length);
        System.arraycopy(shuffeledGenes2, splitIndex + 1, crossedGenes,
                splitIndex + 1, length - splitIndex + 1);

        Genome out = new Genome(crossedGenes);
        out.correctGenes();
        return out;
    }

    private static int[] getShuffledArray(int[] array)
    {
        int[] out = Arrays.copyOf(array, length);
        for (int i=array.length-1; i>0; --i)
        {
            int index = random.nextInt(i + 1);
            int temp = out[index];
            out[index] = out[i];
            out[i] = temp;
        }
        return out;
    }
}
