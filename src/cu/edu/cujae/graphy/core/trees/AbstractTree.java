/*
 * Copyright (C) 2022 CUJAE.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cu.edu.cujae.graphy.core.trees;

import cu.edu.cujae.graphy.core.Tree;
import cu.edu.cujae.graphy.core.TreeNode;
import cu.edu.cujae.graphy.core.abstractions.AbstractGraph;

/**
 * A utility implementation of the {@link Tree} interface.
 *
 * @author Javier Marrero
 * @param <E>
 */
public abstract class AbstractTree<E> extends AbstractGraph<E> implements Tree<E>
{

    /**
     * Returns the maximum within an unspecified set of parameters.
     *
     * @param args
     *
     * @return
     */
    public static int max(int[] args)
    {
        if (args.length == 0)
        {
            throw new IllegalArgumentException("need at least one element to return the maximum.");
        }

        int result = args[0];
        for (int i = 1; i < args.length; ++i)
        {
            if (args[i] > result)
            {
                result = args[i];
            }
        }
        return result;
    }

    public AbstractTree()
    {
        super(true);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getHeight()
    {
        return recursiveHeight(getRoot());
    }

    private int recursiveHeight(TreeNode<E> node)
    {
        if (isRoot(node) && !node.hasChildren())
        {
            return 0;
        }
        else
        {
            int[] array = new int[node.getChildren().size()];
            int k = 0;
            for (TreeNode<E> n : node.getChildren())
            {
                array[k++] = recursiveHeight(n);
            }

            return max(array);
        }
    }
}
