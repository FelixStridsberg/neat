package com.vadeen.neat.genome.diff;

import com.vadeen.neat.gene.ConnectionGene;
import com.vadeen.neat.genome.Genome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The genome diff factory generates diffs between two genomes.
 */
public class GenomeDiffFactory {
   private final Genome left;
   private final Genome right;

   public GenomeDiffFactory(Genome left, Genome right) {
      this.left = left;
      this.right = right;
   }

   public GenomeDiff getDiff() {
      Diff<Genome> genomes = new Diff<>(left, right);
      Diff<Integer> innovations = diffInnovations();
      Diff<List<ConnectionGene>> excess = diffExcess(innovations);
      Diff<List<ConnectionGene>> matching = new Diff<>(new ArrayList<>(), new ArrayList<>());
      Diff<List<ConnectionGene>> disjoint = new Diff<>(new ArrayList<>(), new ArrayList<>());

      // Diff matching and disjoint at the same time for performance reasons.
      diffMatchingAndDisjoint(genomes, innovations, matching, disjoint);

      return new GenomeDiff(genomes, disjoint, excess, matching);
   }

   private void diffMatchingAndDisjoint(
           Diff<Genome> genomes,
           Diff<Integer> innovations,
           Diff<List<ConnectionGene>> matching,
           Diff<List<ConnectionGene>> disjoint
   ) {
      int minInnovation = Math.min(innovations.getLeft(), innovations.getRight());
      for (int i = 1; i <= minInnovation; i++) {
         ConnectionGene leftCon = genomes.getLeft().getConnections().get(i);
         ConnectionGene rightCon = genomes.getRight().getConnections().get(i);

         if (leftCon != null && rightCon != null) {
            matching.getLeft().add(leftCon);
            matching.getRight().add(rightCon);
         }
         else if (leftCon != null) {
            disjoint.getLeft().add(leftCon);
         }
         else if (rightCon != null) {
            disjoint.getRight().add(rightCon);
         }
      }
   }

   private Diff<List<ConnectionGene>> diffExcess(Diff<Integer> innovations) {
      if (innovations.getLeft() > innovations.getRight())
         return new Diff<>(getExcess(left, innovations.getRight()), new ArrayList<>());
      else if (innovations.getLeft() < innovations.getRight())
         return new Diff<>(new ArrayList<>(), getExcess(right, innovations.getLeft()));
      return new Diff<>(new ArrayList<>(), new ArrayList<>());
   }

   private Diff<Integer> diffInnovations() {
      int maxInnovationLeft = 0;
      if (!left.getConnections().isEmpty())
         maxInnovationLeft = Collections.max(left.getConnections().keySet());

      int maxInnovationRight = 0;
      if (!right.getConnections().isEmpty())
         maxInnovationRight = Collections.max(right.getConnections().keySet());

      return new Diff<>(maxInnovationLeft, maxInnovationRight);
   }

   private List<ConnectionGene> getExcess(Genome g, int above) {
      return g.getConnections().values().stream()
              .filter(x -> x.getInnovation() > above)
              .sorted(Comparator.comparing(ConnectionGene::getInnovation))
              .collect(Collectors.toList());
   }
}
