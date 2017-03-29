# PrimerDesign

The Primer Design terminal program produces thermodynamically stable primer pairs for qPCR from an input of aligned nucleotide sequences. This is the main subcommand known as select. A preliminary subcommand is screen which produces a graph of the coverage and the entropy for each position of the input reference sequences. The input nucleotide sequences should be in the FASTA format. 

## Set Up

Some commands depend on RDP ReadSeq and the open source program primer3 that can be found online. See RDPTools (https://github.com/rdpstaff/RDPTools) to install ReadSeq. 

## Usage

java -Xmx8g -jar /path/to/PrimerDesign.jar [--help] 
          
    --assayMax <assayMax>                               assayMax (default:
                                                        20) - Maxiumum
                                                        number of assays
                                                        allowed - one
                                                        degenerate primer
                                                        pair per assay
    --degenMax <degenMax>                               degenMax (default:
                                                        8) - Maximum
                                                        degeneracy per
                                                        primer pair.
                                                        Nondegenerate
                                                        primers = 1.
                                                        Recommend no
                                                        higher than 10
    --forwardMaxPos <forwardMaxPos>                     forwardMaxPos
                                                        (default: 120) -
                                                        Foward oligo
                                                        maximum position
                                                        to end
                                                        enumeration.
                                                        Needed if
                                                        SlidingScale is
                                                        false
    --forwardMinPos <forwardMinPos>                     forwardMinPos
                                                        (default: 100) -
                                                        Foward oligo
                                                        minimum position
                                                        to begin
                                                        enumeration.
                                                        Needed if
                                                        SlidingScale is
                                                        false
    --GCFilterMax <GCFilterMax>                         GCFilterMax
                                                        (default: 0.70) -
                                                        G+C content filter
                                                        maximum percent.
                                                        Recommend leaving
                                                        at default if
                                                        unsure
    --GCFilterMin <GCFilterMin>                         GCFilterMin
                                                        (default: 0.45) -
                                                        G+C content filter
                                                        minimum percent.
                                                        Recommend leaving
                                                        at default if
                                                        unsure
 ```                                                       
 -h,--help                                              Shows this help
    --hairMax <hairMax>                                 hairMax (default:
                                                        35) - Hairpin
                                                        maximum
                                                        temperature
    --homoMax <homoMax>                                 homoMax (default:
                                                        35) - Homodimer
                                                        maximum
                                                        temperature
 -i,--input <input>                                     Input .fasta or
                                                        .fa file. Contains
                                                        aligned gene
                                                        sequences
    --isHenikoffWeightNeeded <isHenikoffWeightNeeded>
                                                        isHenikoffWeightNe
                                                        eded (default:
                                                        false) - Henikoff
                                                        Weighting Method.
                                                        Set to 'true' or
                                                        't' if you would
                                                        like this
                                                        weighting method
                                                        results aswell as
                                                        uniform results.
                                                        Will give the
                                                        highest weight to
                                                        unique sequences
                                                        based on bases at
                                                        each position.
    --isTreeWeightNeeded <isTreeWeightNeeded>           isTreeWeightNeeded
                                                        (default: false)-
                                                        A phylogenetic
                                                        tree weighting
                                                        method.  Must
                                                        include .tree
                                                        file. Set to
                                                        'true' or 't' if
                                                        you would like
                                                        this weighting
                                                        method results
                                                        aswell as uniform
                                                        results. Will give
                                                        the highest weight
                                                        to sequences
                                                        closest to the
                                                        root
    --magnesConc <magnesConc>                           magnesConc
                                                        (default: 1.5) -
                                                        Divalent magnesium
                                                        concentration for
                                                        thermodynamic
                                                        calculations
    --maxMismatches <maxMismatches>                     maxMismatches
                                                        (default: 2) -
                                                        Oligo mismatch
                                                        maximum. Recommend
                                                        setting between 0
                                                        and 3
    --NoPoly3GCFilter <NoPoly3GCFilter>                 NoPoly3GCFilter
                                                        (default: true) -
                                                        If true, filter to
                                                        remove any oligo
                                                        generated with
                                                        three Guanines or
                                                        three Cytosines in
                                                        a row
    --NoTEndFilter <NoTEndFilter>                       NoTEndFilter
                                                        (default: true) -
                                                        If true, filter to
                                                        remove any oligo
                                                        generated with a
                                                        Thymine base at
                                                        end
 -o,--GraphOutput <GraphOutput>                         If screen
                                                        subcommand is ran,
                                                        then this is the
                                                        output directory
                                                        for graph results
    --oligoMaxSize <oligoMaxSize>                       oligoMaxSize
                                                        (default: 30) -
                                                        Maximum oligo
                                                        size; Recommend no
                                                        longer than 30 bp
    --oligoMinSize <oligoMinSize>                       oligoMinSize
                                                        (default: 22) -
                                                        Minimum oligo
                                                        size; Recommend no
                                                        shoter than 15 bp
    --os <os>                                           Operating System :
                                                        linux/mac
                                                        (default: mac)
    --output <output>                                   Full path to
                                                        output file (for
                                                        select)
    --PolyRunFilter <PolyRunFilter>                     PolyRunFilter
                                                        (default: 4) -
                                                        Poly Run max
                                                        filter
    --productLengthMax <productLengthMax>               productLengthMax
                                                        (default: 200) -
                                                        Maximum amplicon
                                                        product length.
                                                        Needed if
                                                        SlidingScale is
                                                        true
    --productLengthMin <productLengthMin>               productLengthMin
                                                        (default: 100) -
                                                        Minimum amplicon
                                                        product length.
                                                        Needed if
                                                        SlidingScale is
                                                        true
    --reverseMaxPos <reverseMaxPos>                     reverseMaxPos
                                                        (default: 370) -
                                                        Reverse oligo
                                                        maximum position
                                                        to end
                                                        enumeration.
                                                        Needed if
                                                        SlidingScale is
                                                        false
    --reverseMinPos <reverseMinPos>                     reverseMinPos
                                                        (default: 350) -
                                                        Reverse oligo
                                                        minimum position
                                                        to begin
                                                        enumeration.
                                                        Needed if
                                                        SlidingScale is
                                                        false
    --SlidingScale <SlidingScale>                       SlidingScale
                                                        (default: true) -
                                                        If true, then
                                                        forward and
                                                        reverse primer
                                                        pairs are built
                                                        with a sliding
                                                        window between the
                                                        given amplicon
                                                        product minimum
                                                        and maximum
                                                        length. If false,
                                                        the pairs will be
                                                        built between the
                                                        forward and
                                                        reverse given min
                                                        and max.
    --sodiumConc <sodiumConc>                           sodiumConc
                                                        (default: 50) -
                                                        Monovalent sodium
                                                        concentration for
                                                        thermodynamic
                                                        calculations
    --subcommand <subcommand>                           select subcommand:
                                                        select - file
                                                        output of
                                                        individual
                                                        degenerate primer
                                                        pairs with
                                                        sequences
                                                        theoretically hit
                                                        / screen - graph
                                                        output of the
                                                        proportional
                                                        theoretical
                                                        uniform coverage
                                                        at each position
                                                        (default: select)
 -t,--treeinput <treeinput>                             File (.tree)
                                                        containing
                                                        phylogentic
                                                        relationship of
                                                        the input
                                                        sequences. In
                                                        Newick format.
                                                        Must have if tag
                                                        -isTreeWeightNeede
                                                        d is true
    --tempMax <tempMax>                                 tempMax (default:
                                                        63) - Oligo max
                                                        melting
                                                        temperature
    --tempMin <tempMin>                                 tempMin (default:
                                                        55) - Oligo min
                                                        melting
                                                        temperature
                                                        
```                                                        

RDP Primer Design Subcommands: Screen and Select

Screen – command used to generate a graphical representation of the coverage and entropy at each position of the reference set. A proportional bar graph of the theoretical uniform coverage is calculated at any given position as the summation of all possible targets hit divided by the total number of sequences. 

Sample Screen Command
```
java -jar /path/to/PrimerDesign.jar -subcommand screen -input /path/to/refSequences.fasta -GraphOutput /path/to/userDir -oligoMinSize 15 -oligoMaxSize 30 -tempMin 55 -tempMax 63 -hairMax 35 -homoMax 35 -os mac -NoTEndFilter t -NoPoly3GCFilter t -PolyRunFilter 4 -GCFilterMin 0.45 -GCFilterMax 0.7 -assayMax 30 -degenMax 6 -sodiumConc 50.0 -magnesConc 1.5
```

Select – command that generates an output file of primer pairs with its corresponding target sequences. Initial program settings given by the user for the user’s record are also given as output. 

A user should consider the benefits of the insight from the screen results when running select. 

Perhaps there are high peaks of coverage slightly outside of the user’s initial desired amplicon product minimum and maximum lengths. The tag ‘IsSlidingScale’ can be set to ‘false’ to stop the generation of primer pairs from all positions with a sliding window. Then, set the forward and reverse minimum and maximum to position ranges that have high coverage and relatively moderate to low richness. 

Sample Select Command (without Sliding Scale)
```
java -jar /path/to/PrimerDesign.jar -subcommand select -input /path/to/refSequences.fasta -SlidingScale false -forwardMinPos 100 -forwardMaxPos 150 - reverseMinPos 425 -reverseMaxPos 450 -oligoMinSize 15 -oligoMaxSize 30 -maxMismatches 2 -tempMin 55 -tempMax 63 -hairMax 35 -homoMax 35 -isTreeWeightNeeded f -isHenikoffWeightNeeded t -os mac -output /work/leotift/nifHGrp2CmdLineResults -assayMax 30 -degenMax 6 -NoTEndFilter t -NoPoly3GCFilter t -PolyRunFilter 4 -GCFilterMin 0.45 -GCFilterMax 0.7 -sodiumConc 50.0 -magnesConc 1.5
```




Perhaps coverage does not vary significantly based upon position. This would be the time to just use the sliding window with a product length minimum and maximum.  

Sample Select Command (with Sliding Scale)
```
java -jar /path/to/PrimerDesign.jar -subcommand select -input /path/to/refSequences.fasta -SlidingScale true -productLengthMin 150 -productLengthMax 250 -oligoMinSize 15 -oligoMaxSize 30 -maxMismatches 2 -tempMin 55 -tempMax 63 -hairMax 35 -homoMax 35 -isTreeWeightNeeded f -isHenikoffWeightNeeded t -os mac -output /work/leotift/CmdLineResults -assayMax 30 -degenMax 6 -NoTEndFilter t -NoPoly3GCFilter t -PolyRunFilter 4 -GCFilterMin 0.45 -GCFilterMax 0.7 -sodiumConc 50.0 -magnesConc 1.5
```

Sample Output File of the Select subcommand 
(Input parameters, degenerate pairs with foward and reverse oligos, and sequences hit by each pair):
```
Michigan State University: RDP Primer Design
--------------------------------------------
Program User Settings
--------------------------------------------
Fasta file: /work/leotift/nifDgr3.fasta
Sliding Scale - t or f? false
Tree Weighted - t or f? false
Henikoff Weighted - t or f? false
Forward Start Position: 840
Forward End Position: 900
Reverse Start Position: 1130
Reverse End Position: 1170
Filters: T end: true, No Poly3GC: true, Poly Run: 4, GC Content:0.35 - 0.6
Oligo Min Size: 17
Oligo Max Size: 28
Max Number Mismatches: 0
Melting Temp Min: 55.0
Melting Temp Max: 62.0
Homodimer Max: 35.0
Hairpin Max: 35.0
Sodium Conc: 50.0
Magnesium Conc: 1.5
Max Degeneracy for each primer: 8
Max Num of Assays: 30
Sequence Length: 2343
--------------------------------------------
Primer Pair: DegeneratePair{fwdOligo=[Oligo{seq=ATCTCAATCTGCAGGTCACCA}{ Tm:58.65179472145502}{EndPos:899}
, Oligo{seq=CAATCAGCCGGTCACCA}{ Tm:56.34783911091415}{EndPos:899}
, Oligo{seq=GTAAGTCAATCAGCAGGACACCA}{ Tm:60.12327281754136}{EndPos:899}
, Oligo{seq=GAGTAACTCAATCAGCAGGTCATCA}{ Tm:60.18887096375721}{EndPos:899}
, Oligo{seq=AGCCAATCTGCAGGTCATCA}{ Tm:58.94355676316599}{EndPos:899}
, Oligo{seq=TATCTCAATCAGCAGGACATCA}{ Tm:56.262480666380384}{EndPos:899}
, Oligo{seq=TATCTCAATCAGCAGGGCACCA}{ Tm:61.0491700371382}{EndPos:899}
, Oligo{seq=CCCAATCTGCAGGACATCA}{ Tm:56.68496896549226}{EndPos:899}
] revOligo=[Oligo{seq=GAATAACATTCAAATCAGCAGTGTG}{ Tm:57.072928924899145}{EndPos:1140}
, Oligo{seq=ACTAGGTTTAAGTCTGCTTTGTC}{ Tm:56.05430626064259}{EndPos:1140}
, Oligo{seq=ACCAAGTTAAGTTCTGCAGTATG}{ Tm:56.37016198994928}{EndPos:1140}
, Oligo{seq=CCATGTTAAGGTCGGCTGTATG}{ Tm:58.703122878983265}{EndPos:1140}
, Oligo{seq=GGACTAGATTTAAATCTGCAGTATG}{ Tm:55.190491728626284}{EndPos:1140}
, Oligo{seq=GTACTAAGTTTAAATCCGCTGTATC}{ Tm:55.47307144157952}{EndPos:1140}
, Oligo{seq=TGCACTAAGTTTACATCTGCAGTGTG}{ Tm:61.5064061970719}{EndPos:1140}
, Oligo{seq=CCAAATTTACGTCTGCCTGATA}{ Tm:56.08567303175715}{EndPos:1140}
]}
Sequences hit
CP000721
CP002660
AE001437
CP016280
CP002118
AKVM01000053
LBCX01000114
LROS01000077
AKVN02000001
CP006763
CP010086
AKVJ01000024
CP006777
CBXI010000026
ABDT01000049
LBCY01000198
JPGY01000007
AF266462
JPGY02000001
CP011966
AYXR01000045
ANZB01000018
MBAF01000026
CP012395
ACOM01000001
CP014170
AY603957
AZLX01000216
LBCZ01000223
CP009268
CP009267
AY649324
AKVN01000021
LBCW01000051
AKVK01000143
AKVL01000569
```

