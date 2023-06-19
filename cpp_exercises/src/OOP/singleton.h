

template <class T>
class Singleton
{
    static T* instance;         // it can be improved by using a smart pointer

    public:
        static T* getInstance()
        {
            if (instance == nullptr)
            {
                instance = new T();
            }
            return instance;
        }
};