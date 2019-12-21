package agh.cs.lab8;

import java.util.*;

public class GeneticCode {
    private int [] dna=new int[32];
    private Random generator = new Random();
    private final int dnaNum =32;
    private final int genesNum=8;

    public GeneticCode(){
        for(int i = 0; i< dnaNum; i++){
            this.dna[i]=generator.nextInt(genesNum);
        }
        CheckGenes(this.dna);


    }

    public GeneticCode (GeneticCode firstParent, GeneticCode secondParent){
        int index1=generator.nextInt(dnaNum);;
        int index2;

        do {
            index2 = generator.nextInt(dnaNum);
        }while(index1==index2);

        if(index1>index2){
            int tmp=index1;
            index1=index2;
            index2=tmp;
        }
        for(int i = 0; i< dnaNum; i++){
            if(i<=index1 || i>=index2){
                this.dna[i]=firstParent.dna[i];
            }
            else{
                this.dna[i]=secondParent.dna[i];
            }
        }
        CheckGenes(this.dna);


    }

    public void CheckGenes (int [] dnaCode){

        int [] typesOfGenes= new int [genesNum];
        for(int i=0; i<genesNum;i++){
            typesOfGenes[i]=0;
        }

        for(int i = 0; i< dnaNum; i++){
            typesOfGenes[dnaCode[i]]++;
        }

        for (int i=0; i<genesNum; i++){
            int x;
            if( typesOfGenes[i] == 0 ){
                do {
                    x = generator.nextInt(dnaNum);
                } while( typesOfGenes[dnaCode[x]]<2 );
                typesOfGenes[i]++;
                typesOfGenes[dnaCode[x]]--;
            }
        }

        int index=0;
        for( int i=0; i<genesNum; i++){
            do{
                dnaCode[index]=i;
                typesOfGenes[i]--;
                index++;
            } while (typesOfGenes[i]>0);
        }
    }



    @Override
    public String toString (){
        StringBuilder s= new StringBuilder();
        for(int i = 0; i< dnaNum; i++){
            s.append(Integer.toString(dna[i]));
        }
        return s.toString();
    }

    @Override
    public int hashCode() {
        int hash = 13;
        for(int i=0; i<dnaNum; i++) {
            if(i%2==0)
            hash += this.dna[i] * 31;
            else
            hash += this.dna[i] * 17;
        }
        return hash;
    }


    public int getGeneticCodeByIndex(int i){
        return this.dna[i];
    }

    public int [] getGeneticCode(){
        return this.dna;
    }
}
