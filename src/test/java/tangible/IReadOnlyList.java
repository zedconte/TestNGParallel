package tangible;

public interface IReadOnlyList<E> extends IReadOnlyCollection<E>
{
    E get(int index);
}
