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
package cu.edu.cujae.graphy.core.iterators;

import cu.edu.cujae.graphy.core.Edge;
import cu.edu.cujae.graphy.core.Graph;
import cu.edu.cujae.graphy.core.Node;
import cu.edu.cujae.graphy.core.abstractions.AbstractGraph;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This class defines an abstract iterator class: an utility class that may be used to ease implementation of several
 * iterator functionalities.
 *
 * @author Javier Marrero
 * @param <T>
 */
public abstract class AbstractGraphIterator<T> implements GraphIterator<T>
{

    private final Graph<T> graph;
    private Node<T> current;

    /**
     * Construye un nuevo objeto iterador abstracto.
     *
     * @param graph
     * @param current
     */
    protected AbstractGraphIterator(Graph<T> graph, Node<T> current)
    {
        this.graph = graph;
        this.current = current;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T back()
    {
        throw new UnsupportedOperationException("This operation is not supported by this particular iterator.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Edge> getAllAdjacentEdges()
    {
        Set<Edge> candidates = new CopyOnWriteArraySet<>(getEdgesDepartingSelf());
        candidates.addAll(getEdgesArrivingSelf());

        return Collections.unmodifiableCollection(candidates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Integer> getAllAdjacentVertices()
    {
        return current.getAllAdjacentVertices();
    }

    @Override
    public Collection<Integer> getAdjacentVerticesArrivingSelf()
    {
        return current.getAllVerticesArrivingSelf();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Edge getAdjacentEdge(int v)
    {
        if (graph instanceof AbstractGraph)
        {
            return current.getAdjacentEdge(((AbstractGraph<T>) graph).findNodeByLabel(v));
        }
        else
        {
            for (Edge e : getEdgesDepartingSelf())
            {
                if (e.getFinalNode().getLabel() == v)
                {
                    return e;
                }
            }
            for (Edge e : getEdgesArrivingSelf())
            {
                if (e.getStartNode().getLabel() == v)
                {
                    return e;
                }
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Edge> getEdgesDepartingSelf()
    {
        return getCurrent().getEdgesDepartingSelf();
    }

    @Override
    public Collection<Integer> getAdjacentVerticesDepartingSelf()
    {
        return current.getAllVerticesDepartingSelf();
    }
    
    /**
     * @return the current
     */
    protected Node<T> getCurrent()
    {
        return current;
    }

    @Override
    public Collection<Edge> getEdgesArrivingSelf()
    {
        return getCurrent().getEdgesArrivingSelf();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getLabel()
    {
        return current.getLabel();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isAdjacent(Node<T> node)
    {
        return current.isAdjacent(node);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isAdjacent(GraphIterator<T> it)
    {
        return graph.isVertexAdjacent(current.getLabel(), it.getLabel());
    }

    /**
     * {@inheritDoc }
     * <p>
     * This particular implementation is a stub and produces an exception whenever someone tries to random access a
     * node.
     */
    @Override
    public T next(Node<T> target)
    {
        throw new UnsupportedOperationException("This operation is not supported by this particular iterator");
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public T next(int u)
    {
        throw new UnsupportedOperationException("This operation is not supported by this particular iterator");
    }

    /**
     * @param current the current to set
     */
    protected void setCurrent(Node<T> current)
    {
        this.current = current;
    }

}
