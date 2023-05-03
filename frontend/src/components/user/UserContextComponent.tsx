import {createContext, Dispatch, PropsWithChildren, SetStateAction, useState} from "react";

export interface User {
    id: number;
    username: string;
}

export type UserContextType = {
    user: User | undefined;
    setUser: Dispatch<SetStateAction<User | undefined>> | undefined;
}

export const UserContext = createContext<UserContextType | undefined>(undefined);

/**
 * Wrapper component used to store global user state for reference by all child components.
 * @param props Component properties, children property is used to construct JSX element.
 * @constructor
 */
function UserContextComponent(props: PropsWithChildren) {

    const [user, setUser] = useState<User | undefined>(undefined);

    return (
        <UserContext.Provider value={{user, setUser}}>
            {props.children}
        </UserContext.Provider>
    );
}

export default UserContextComponent;